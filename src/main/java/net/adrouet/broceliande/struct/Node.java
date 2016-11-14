package net.adrouet.broceliande.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Node<T, U> {

    private String label;

    private List<Node> children;

    private Predicate<T> predicate;

    private U result;

    public Node(){
        this.predicate = (T t) -> false;
        this.children = new ArrayList<>();
    }

    public Node <T, U> getChild(T data){
        return children.stream()
                .filter(c -> c.predicate.test(data))
                .findFirst()
                .orElse(null);
    }

    public boolean isLeaf(){
        return result != null;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Predicate<T> getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    public U getResult() {
        return result;
    }

    public void setResult(U result) {
        this.result = result;
    }
}
