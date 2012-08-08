package x.preferd.cmd;

import java.util.prefs.Preferences;

import x.preferd.ExecutionError;
import x.preferd.NodeKeyValue;
import x.preferd.Options;
import x.preferd.UsageException;


public class Copy extends AbstractCommand {

    protected final boolean move;

    protected Copy(boolean move) {
	this.move = move;
    }

    public Copy() {
	this(false);
    }

    @Override
    public void execute(Options opts) throws Exception {

	NodeKeyValue from = new NodeKeyValue(opts);
	NodeKeyValue to = new NodeKeyValue(opts, false);
	if (!from.isNode()) {
	    throw new UsageException();
	}
	if (!from.nodeExists()) {
	    throw new ExecutionError("Node not found: " + from.getNode());
	}
	Preferences fromNode = from.extractNode();
	Preferences toNode = (to.isNode() ? to.extractNode() : fromNode);
	boolean sameNode = fromNode.toString().equals(toNode.toString());

	if (from.isKey()) {
	    String value = fromNode.get(from.getKey(), null);
	    if (value == null) {
		throw new ExecutionError("Key not found: " + from.getNode()
			+ " " + from.getKey());
	    }

	    String toKey = (to.isKey() ? to.getKey() : from.getKey());
	    boolean sameKey = from.getKey().equals(toKey);
	    if (sameNode && sameKey) {
		return;
	    }
	    
	    toNode.put(toKey, value);
	    if (move) {
		fromNode.remove(from.getKey());
	    }
	} else {
	    if (sameNode) {
		return;
	    }
	    copy(fromNode, toNode);
	    if (move) {
		fromNode.removeNode();
	    }
	}
	toNode.flush();
	fromNode.flush();
    }

    private void copy(Preferences f, Preferences t) throws Exception {
	String[] keys = {};
	if (f.parent() != null) {
	    keys = f.keys();
	}
	String[] children = f.childrenNames();
	for (String key : keys) {
	    String value = f.get(key, null);
	    if (value != null) {
		t.put(key, value);
	    }
	}
	for (String child : children) {
	    copy(f.node(child), t.node(child));
	}
    }
}