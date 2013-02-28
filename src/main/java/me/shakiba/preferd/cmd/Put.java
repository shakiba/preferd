package me.shakiba.preferd.cmd;

import java.util.prefs.Preferences;

import me.shakiba.preferd.NodeKeyValue;
import me.shakiba.preferd.Options;
import me.shakiba.preferd.UsageException;



public class Put extends AbstractCommand {

    @Override
    public void execute(Options opts) throws Exception {

	NodeKeyValue path = new NodeKeyValue(opts);
	if (!path.isNode() || (path.isKey() && !path.isValue())) {
	    throw new UsageException();
	}
	Preferences node = path.getRoot().node(path.getNode());
	if (path.isKey()) {
	    // BufferedReader br = new BufferedReader(new InputStreamReader(
	    // System.in));
	    // value = "";
	    // String line;
	    // while ((line = br.readLine()) != null) {
	    // value += line;
	    // }
	    node.put(path.getKey(), path.getValue());
	}
	node.flush();
    }
}