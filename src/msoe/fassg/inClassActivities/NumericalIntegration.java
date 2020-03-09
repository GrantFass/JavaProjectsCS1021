/*
 * Course:     MA 137
 * Winter 2019
 * File header contains class NumericalIntegration
 * Name:       fassg
 * Created:    2/19/2020
 */
package msoe.fassg.inClassActivities;

/**
 * Course: MA 137
 * Winter 2019
 * NumericalIntegration purpose: Estimating area
 * under a curve using different numerical integration methods
 *
 * @author fassg
 * @version created on 2/19/2020 at 1:03 PM
 */
public class NumericalIntegration {
    /**
     * lower bounds of integration
     */
    private double a;
    /**
     * upper bounds of integration
     */
    private double b;
    /**
     * the number of intervals to evaluate across
     */
    private int n;
    /**
     * the equation of the curve to evaluate the area under
     */
    private Function function;
    /**
     * the h value for approximation
     * h = ((b - a) / n)
     */
    private double h;
    /**
     * the fractional modifier for trapezoidal integration
     */
    private double trapezoidalModifier = 1 / 2.;
    /**
     * the fractional modifier for simpson's integration
     */
    private double simpsonModifier = 1 / 3.;

    /**
     * Functional interface to define functions to evaluate
     */
    @FunctionalInterface
    public interface Function {
        /**
         * functional interface method to define the equation of the curve
         *
         * @param x the x location to evaluate the curve at
         * @return the value of the curve at x
         */
        double function(double x);
    }

    /**
     * constructor for the numerical integration class
     *
     * @param a        the start location of the definite integral
     * @param b        the end location of the definite integral
     * @param n        the number of intervals to evaluate across
     * @param function the equation of the curve to evaluate under.
     */
    public NumericalIntegration(double a, double b, int n, Function function) {
        this.a = a;
        this.b = b;
        this.n = n;
        this.function = function;
        setH(a, b);
    }

    /**
     * changes the value of n
     *
     * @param n the number of intervals to use to approximate integration.
     */
    public void setN(int n) {
        this.n = n;
        setH(a, b);
    }

    /**
     * sets the value of h based on a new lower bounds and upper bounds
     *
     * @param a lower bounds of integration
     * @param b upper bounds of integration
     */
    public void setH(double a, double b) {
        this.h = ((b - a) / (double) n);
    }

    /**
     * method for approximating area under a curve using left Endpoints
     * Integral from a->b of f(x) = ((b-a)/n)(y + y1 + ... + yn-1)
     *
     * @return the numerical approximation of the area under the curve
     */
    private double leftEndpointApproximation() {
        double sum = 0;
        //from 0 -> n-1
        for (int i = 0; i < n; i++) {
            sum += function.function(h * i + a);
        }
        return h * sum;
    }

    /**
     * method for approximating area under a curve using right Endpoints
     * Integral from a->b of f(x) = ((b-a)/n)(y + y1 + ... + yn)
     *
     * @return the numerical approximation of the area under the curve
     */
    private double rightEndpointApproximation() {
        double sum = 0;
        //from 1 -> n
        for (int i = 1; i <= n; i++) {
            sum += function.function(h * i + a);
        }
        return h * sum;
    }

    /**
     * method for approximating area under a curve using midpoints
     * Integral from a->b of f(x) = ((b-a)/n)(y + y1 + ... + yn)
     *
     * @return the numerical approximation of the area under the curve
     */
    private double midpointApproximation() {
        double sum = 0;
        //from 1 -> n
        for (int i = 0; i < n; i++) {
            sum += function.function((h * (2 * i + 1)) / 2. + a);
        }
        return h * sum;
    }

    /**
     * method for approximating area under a curve using a trapezoidal method
     * Integral from a->b of f(x) = ((b-a)/(2n))(y + 2y1 + 2y2 + ... + 2yn-1 + yn)
     *
     * @return the numerical approximation of the area under the curve
     */
    private double trapezoidalApproximation() {
        double sum = 0;
        //from 0 -> n
        for (int i = 0; i <= n; i++) {
            if (i == 0) {
                sum += function.function(h * i + a);
            } else if (i == n) {
                sum += function.function(h * i + a);
            } else {
                sum += 2 * function.function(h * i + a);
            }
        }
        return h * trapezoidalModifier * sum;
    }

    /**
     * method for approximating area under a curve using the simpson's method
     * Integral from a->b of f(x) = ((b-a)/(3n))(y + 4y1 + 2y2 + 4y3 + 2y4 + ...
     * + 2yn-2 + 4yn-1 + yn)
     *
     * @return the numerical approximation of the area under the curve
     */
    private double simpsonApproximation() {
        double sum = 0;
        //from 0 -> n
        for (int i = 0; i <= n; i++) {
            if (i == 0) {
                sum += function.function(h * i + a);
            } else if (i == n) {
                sum += function.function(h * i + a);
            } else if (i % 2 == 0) {
                sum += 2 * function.function(h * i + a);
            } else {
                sum += 4 * function.function(h * i + a);
            }
        }
        return h * simpsonModifier * sum;
    }

    /**
     * general method to run all approximations at the same time
     * [0] = leftApprox.
     * [1] = midApprox.
     * [2] = rightApprox.
     * [3] = trapezoidApprox.
     * [4] = simpsonApprox.
     *
     * @return the value of the approximations
     */
    private double[] approximate() {
        final int numberOfApproximationTypes = 5;
        double[] approximations = new double[numberOfApproximationTypes];
        //initialize the array
        for (int i = 0; i < numberOfApproximationTypes; i++) {
            approximations[i] = 0;
        }
        //approximate on the interval [0, n]
        for (int i = 0; i <= n; i++) {
            //leftApprox.
            if (i != n) {
                approximations[0] += function.function(h * i + a);
            }
            //midApprox.
            approximations[1] += function.function((h * (2 * i + 1)) / 2. + a);
            //rightApprox.
            if (i != 0) {
                approximations[2] += function.function(h * i + a);
            }
            //trapezoidApprox.
            if (i == 0 || i == n) {
                approximations[3] += function.function(h * i + a);
            } else {
                approximations[3] += 2 * function.function(h * i + a);
            }
            //SimpsonApprox.
            if (i == 0 || i == n) {
                approximations[4] += function.function(h * i + a);
            } else if (i % 2 == 1) {
                approximations[4] += 4 * function.function(h * i + a);
            } else {
                approximations[4] += 2 * function.function(h * i + a);
            }
        }
        approximations[0] *= h;
        approximations[1] *= h;
        approximations[2] *= h;
        approximations[3] *= h * trapezoidalModifier;
        approximations[4] *= h * simpsonModifier;
        return approximations;
    }

    @Override
    public String toString() {
        return "Left Endpoint Approximation:     " + leftEndpointApproximation() +
                "\nMidpoint Approximation:       " + midpointApproximation() +
                "\nRight Endpoint Approximation: " + rightEndpointApproximation() +
                "\nTrapezoidal Approximation:    " + trapezoidalApproximation() +
                "\nSimpson's Approximation:      " + simpsonApproximation();
    }

    /**
     * method to print the output of all the integration
     */
    public void approximateAllAndPrintOutput() {
        double[] approximations = approximate();
        System.out.format("Approximations using n = %,d\n" +
                        "%35s%-20s\n%35s%-20s\n%35s%-20s\n%35s%-20s\n%35s%-20s\n\n", n,
                "Left Endpoint Approximation: ", approximations[0],
                "Midpoint Approximation: ", approximations[1],
                "Right Endpoint Approximation: ", approximations[2],
                "Trapezoidal Approximation: ", approximations[3],
                "Simpson's Approximation: ", approximations[4]);
    }

    /**
     * method to print the output of all the integration
     */
    public void approximateAndOutputWithErrorAmounts() {
        double[] approximations = approximate();
        final double trueValue = Math.log(2);
        System.out.format("Approximations using n = %,d\n" +
                        "%35s%-20.8f Error: %-20.8f\n" +
                        "%35s%-20.8f Error: %-20.8f\n" +
                        "%35s%-20.8f Error: %-20.8f\n" +
                        "%35s%-20.8f Error: %-20.8f\n" +
                        "%35s%-20.8f Error: %-20.8f\n\n", n,
                "Left Endpoint Approximation: ", approximations[0], approximations[0] - trueValue,
                "Midpoint Approximation: ", approximations[1], approximations[1] - trueValue,
                "Right Endpoint Approximation: ", approximations[2], approximations[2] - trueValue,
                "Trapezoidal Approximation: ", approximations[3], approximations[3] - trueValue,
                "Simpson's Approximation: ", approximations[4], approximations[4] - trueValue);
    }

    /**
     * main method used for testing
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        Function functionE = x -> Math.exp(Math.pow(x, 2)); //f(x) = e^x^2
        Function functionX = x -> 1 / x;
        final double lowerBounds = 1;
        final double upperBounds = 2;
        final int interval1 = 2;
        final int interval2 = 10;
        final int interval3 = 50;
        final int interval4 = 250;
        final int interval5 = 1000;
        final int defaultIntervalAmount = 1000;
        NumericalIntegration integrate = new
                NumericalIntegration(lowerBounds, upperBounds, defaultIntervalAmount, functionX);
        //approximate using n = 2
        integrate.setN(interval1);
        integrate.approximateAndOutputWithErrorAmounts();
        //approximate using n = 10
        integrate.setN(interval2);
        integrate.approximateAndOutputWithErrorAmounts();
        //approximate using n = 50
        integrate.setN(interval3);
        integrate.approximateAndOutputWithErrorAmounts();
        //approximate using n = 250
        integrate.setN(interval4);
        integrate.approximateAndOutputWithErrorAmounts();
        //approximate using n = 1000
        integrate.setN(interval5);
        integrate.approximateAndOutputWithErrorAmounts();

        /*
        //approximates the integral at different values of n
        final int baseInterval = 10;
        final int numberOfIntervalIncreases = 6;
        int n;
        for (int i = 1; i <= numberOfIntervalIncreases; i++) {
            n = (int) Math.pow(baseInterval, i);
            integrate.setN(n);
            System.out.printf("simpson's Approximation using n = %-,15d:   %-20s\n", n,
                    integrate.simpsonApproximation());
        }
        */
    }
}

