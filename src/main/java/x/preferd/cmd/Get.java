package x.preferd.cmd;

import x.preferd.NodeKeyValue;
import x.preferd.Options;
import x.preferd.UsageException;

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