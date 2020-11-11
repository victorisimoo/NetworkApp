/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.beans;

/**
 *
 * @author ayalr
 */
public class Node<Images> {
    Node<Images> right;
    Node<Images> left;
    Images value;
    
    public void setRight(Node<Images> right){ this.right = right; }
    public void setLeft(Node<Images> left){ this.left = left; }
    public void setValue(Images value){ this.value = value; }
    
    public Node<Images> getRight(){ return right; }
    public Node<Images> getLeft(){ return left; }
    public Images getValue(){ return value; }
    
}