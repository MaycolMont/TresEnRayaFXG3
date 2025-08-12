package espol.tresenrayafxg3.models;

import java.util.List;

import espol.tresenrayafxg3.util.TreeNode;

/**
 * Clase que representa un árbol de estados del juego Tres en Raya.
 * Cada nodo del árbol es un estado del tablero y sus hijos son los
 * posibles estados resultantes de los movimientos del jugador.
 */

public class StateTree {
    private TreeNode<Board> root;
    private char maxValue; // IA como jugador max
    private char minValue;

    public StateTree(Board board, int depth) {
        this.root = new TreeNode<>(board);
        this.minValue = board.getNextPlayer();
        this.maxValue = board.getCurrentPlayer(); // valor de IA y próximo valor a jugar
        populate(root, depth, maxValue);
    }

    public void populate(TreeNode<Board> node, int depth, char value) {
        if (depth <= 0) return;

        Board board = node.getData();

        Iterable<Board> generator = new BoardStatesGenerator(board);

        for (Board childState : generator) {
            node.addChild(childState);
        }

        List<TreeNode<Board>> children = node.getChildren();
        char nextValue = (maxValue == value) ? minValue : maxValue;

        for (TreeNode<Board> childNode : children) {
            populate(childNode, depth - 1, nextValue);
        }
    }


    public BoardMove getBestMove() {
        Board bestState = getBestState(root, true, Integer.MIN_VALUE, Integer.MAX_VALUE).getData();
        if (bestState.isFull() || bestState.isFinished()) return bestState.getLastMove();
        return bestState.getPreviousMove();
    }

    // Implementación sin generación del arbol
    private Board minMax(Board board, int depth, boolean maximizingPlayer) {
        if (depth == 0 || board.isFinished()) {
            return board;
        }

        BoardStatesGenerator boardGenerator = new BoardStatesGenerator(board);

        if (maximizingPlayer) {
            Board bestState = null;
            int maxEval = Integer.MIN_VALUE;
            for (Board boardState : boardGenerator) {
                Board eval = minMax(boardState, depth-1, false);
                int childUtility = eval.getUtility(maxValue, minValue);
                if (childUtility > maxEval || bestState == null) {
                    maxEval = childUtility;
                    bestState = eval;
                }
            }
            return bestState; 
        } else {
            Board bestState = null;
            int minEval = Integer.MAX_VALUE;
            for (Board boardState : boardGenerator) {
                Board eval = minMax(boardState, depth-1, true);

                int childUtility = eval.getUtility(maxValue, minValue);
                if (childUtility < minEval || bestState == null) {
                    minEval = childUtility;
                    bestState = eval;
                }
            }
            return bestState;
        }
    }

    // implementación de minMax usando el arbol generado
    private TreeNode<Board> getBestState(TreeNode<Board> node, boolean isMaximizingPlayer, int alpha, int beta) {
        if (node.isLeaf()) {
            return node;
        }

        TreeNode<Board> bestNode = null;
        int bestUtility = isMaximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (TreeNode<Board> child : node.getChildren()) {
            TreeNode<Board> childNode = getBestState(child, !isMaximizingPlayer, alpha, beta);
            Board childState = childNode.getData();
            int childUtility = childState.getUtility(maxValue, minValue);

            if (isMaximizingPlayer) {
                if (childUtility > bestUtility || bestNode == null) {
                    bestUtility = childUtility;
                    bestNode = childNode;
                    node.setData(bestNode.getData());
                }
                alpha = Math.max(alpha, bestUtility);
            } else {
                if (childUtility < bestUtility || bestNode == null) {
                    bestUtility = childUtility;
                    bestNode = childNode;
                    node.setData(bestNode.getData());
                }
                beta = Math.min(beta, bestUtility);
            }

            // Poda alfa-beta
            if (beta <= alpha) {
                break;
            }
        }

        return bestNode != null ? bestNode : node;
    }
}
