/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.tresenrayafxg3.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maycmont
 */
public class TreeNode<T> {
    private T data;
    private List<TreeNode<T>> children;
    
    public TreeNode(T data) {
        this.data = data;
        children = new ArrayList<>();
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    public void addChild(T data) {
        TreeNode<T> newNode = new TreeNode<>(data);
        children.add(newNode);
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public T getLastChild() {
        return children.get(children.size() -1 ).getData();
    }
}
