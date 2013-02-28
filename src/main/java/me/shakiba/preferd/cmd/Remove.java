package me.shakiba.preferd.cmd;

import java.util.prefs.Preferences;

import me.shakiba.preferd.ExecutionError;
import me.shakiba.preferd.NodeKeyValue;
import me.shakiba.preferd.Options;
import me.shakiba.preferd.UsageException;



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