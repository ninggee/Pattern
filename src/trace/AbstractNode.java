package trace;

import java.util.Objects;

/**
 * An abstract representation of an event in a trace.
 */

public class AbstractNode {

    protected long GID;
    protected int ID;
    protected long tid;
    protected TYPE type;
    protected String label;


    public AbstractNode(long GID, int ID, long tid, TYPE type) {
        this.GID = GID;
        this.ID = ID;
        this.tid = tid;
        this.type = type;

        this.label = "other node";
    }

    public AbstractNode(long GID, int ID, long tid, TYPE type, String label) {
        this.GID = GID;
        this.ID = ID;
        this.tid = tid;
        this.type = type;
        this.label = label;
    }

    public long getGID() {
        return GID;
    }

    public void setGID(long GID) {
        this.GID = GID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractNode)) return false;
        AbstractNode that = (AbstractNode) o;
        return getGID() == that.getGID() &&
                getID() == that.getID() &&
                getTid() == that.getTid() &&
                getType() == that.getType() &&
                Objects.equals(getLabel(), that.getLabel());
    }

    @Override
    public String toString() {
        return "AbstractNode{" +
                "GID=" + GID +
                ", ID=" + ID +
                ", tid=" + tid +
                ", type=" + type +
                ", label='" + label + '\'' +
                '}';
    }
}
