package x.preferd.cmd;

import java.util.prefs.Preferences;

import x.preferd.ExecutionError;
import x.preferd.NodeKeyValue;
import x.preferd.Options;
import x.preferd.UsageException;


public class Remove extends AbstractCommand {

    @Override
    public void execute(Options opts) throws Exception {

	NodeKeyValue path = new NodeKeyValue(opts);
	if (!path.isNode()) {
	    throw new UsageException();
	}
	if (!path.getRoot().nodeExists(path.getNode())) {
	    throw new ExecutionError("Not found: " + path.getNode());
	}
	Preferences node = path.getRoot().node(path.getNode());
	if (!path.isKey()) {
	    if (opts.is("r")) {
		node.removeNode();
	    }else {
		throw new ExecutionError("Use -r to remove entire node: " + path.getNode());
	    }
	} else {
	    node.remove(path.getKey());
	}
	node.flush();
    }
}