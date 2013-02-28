package me.shakiba.preferd.cmd;

import me.shakiba.preferd.NodeKeyValue;
import me.shakiba.preferd.Options;
import me.shakiba.preferd.UsageException;

public class Get extends AbstractCommand {

    @Override
    public void execute(Options opts) throws Exception {
	NodeKeyValue path = new NodeKeyValue(opts);
	if (!path.isNode() || !path.isKey()) {
	    throw new UsageException();
	}
	if (path.getRoot().nodeExists(path.getNode())) {
	    String value = path.getRoot().node(path.getNode())
		    .get(path.getKey(), null);
	    if (value != null) {
		System.out.println(value);
	    }
	}
    }
}