package x.preferd.cmd;

import java.util.prefs.Preferences;

import x.preferd.ExecutionError;
import x.preferd.NodeKeyValue;
import x.preferd.Options;
import x.preferd.UsageException;


public class Export extends List {

    @Override
    public void execute(Options opts) throws Exception {
	NodeKeyValue path = new NodeKeyValue(opts);
	if (!path.isNode()) {
	    throw new UsageException();
	}
	if (!path.nodeExists()) {
	    throw new ExecutionError("Node not found: " + path.getNode());
	}
	Preferences node = path.extractNode();
	if (opts.is("prop")) {

	    printNode(node, (opts.is("r") ? Mode.recursive : Mode.none));
	} else {
	    node.exportSubtree(System.out);
	}
    }

    protected void onNode(Preferences node) throws Exception {
	System.out.println("# " + formatNode(node));
    }

    @Override
    protected String formatKey(Preferences node, String key, String value) {
	return key + "=" + value;
    }
}