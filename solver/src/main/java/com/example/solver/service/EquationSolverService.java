package com.example.solver.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EquationSolverService {

    // ------------------------------------------------------------
    // MAIN ENTRY POINT
    // ------------------------------------------------------------
    public List<String> solve(String input) {
        List<String> msgs = new ArrayList<>();

        if (input == null || input.trim().isEmpty()) {
            msgs.add("Please enter coefficients separated by spaces.");
            return msgs;
        }

        String[] parts = input.trim().split("\\s+");
        double[] coeffs = new double[parts.length];

        try {
            for (int i = 0; i < parts.length; i++)
                coeffs[i] = Double.parseDouble(parts[i]);
        } catch (NumberFormatException e) {
            msgs.add("Invalid number format.");
            return msgs;
        }

        if (coeffs.length < 2) {
            msgs.add("At least two coefficients required for NR method.");
            return msgs;
        }

        double[] poly = Arrays.copyOf(coeffs, coeffs.length);
        msgs.add("Solving polynomial using Newton–Raphson method:");
        msgs.add(polynomialToString(poly));

        List<Double> roots = findAllRoots(poly, msgs);

        if (roots.isEmpty()) {
            msgs.add("Failed to find roots with Newton–Raphson (try different initial guess).");
            return msgs;
        }

        for (int i = 0; i < roots.size(); i++)
            msgs.add(String.format("Root %d ≈ %.10f", i + 1, roots.get(i)));

        return msgs;
    }

    // ------------------------------------------------------------
    // FIND ALL ROOTS BY POLYNOMIAL DEFLATION
    // ------------------------------------------------------------
    private List<Double> findAllRoots(double[] poly, List<String> msgs) {
        int degree = poly.length - 1;
        List<Double> roots = new ArrayList<>();

        double[] p = Arrays.copyOf(poly, poly.length);

        for (int i = 0; i < degree; i++) {
            Double root = newtonRaphson(p);
            if (root == null) {
                msgs.add("Newton–Raphson failed to converge for a root.");
                break;
            }
            roots.add(root);
            p = deflatePolynomial(p, root);
        }

        return roots;
    }

    // ------------------------------------------------------------
    // NEWTON–RAPHSON ITERATION
    // ------------------------------------------------------------
    private Double newtonRaphson(double[] poly) {
        double x = 1.0;
        double tol = 1e-10;
        int maxIter = 100;

        for (int i = 0; i < maxIter; i++) {
            double fx = evaluate(poly, x);
            double dfx = derivative(poly, x);

            if (Math.abs(dfx) < 1e-14)
                return null;

            double xNew = x - fx / dfx;

            if (Math.abs(xNew - x) < tol)
                return xNew;

            x = xNew;
        }

        return null;
    }

    // ------------------------------------------------------------
    // HELPER METHODS
    // ------------------------------------------------------------
    private double evaluate(double[] poly, double x) {
        double sum = 0;
        for (int i = 0; i < poly.length; i++) {
            int power = poly.length - 1 - i;
            sum += poly[i] * Math.pow(x, power);
        }
        return sum;
    }

    private double derivative(double[] poly, double x) {
        double h = 1e-6;
        return (evaluate(poly, x + h) - evaluate(poly, x - h)) / (2 * h);
    }

    private double[] deflatePolynomial(double[] poly, double root) {
        int n = poly.length;
        double[] newPoly = new double[n - 1];

        newPoly[0] = poly[0];
        for (int i = 1; i < newPoly.length; i++)
            newPoly[i] = poly[i] + newPoly[i - 1] * root;

        return newPoly;
    }

    private String polynomialToString(double[] p) {
        StringBuilder sb = new StringBuilder("P(x) = ");
        for (int i = 0; i < p.length; i++) {
            int power = p.length - 1 - i;
            sb.append(String.format("%.3f", p[i]));
            if (power > 0) sb.append("x^").append(power);
            if (i != p.length - 1) sb.append(" + ");
        }
        return sb.toString();
    }
}
