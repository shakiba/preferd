package me.shakiba.preferd.cmd;

import java.util.Properties;
import java.util.prefs.Preferences;

import me.shakiba.preferd.NodeKeyValue;
import me.shakiba.preferd.Options;
import me.shakiba.preferd.UsageException;



public class Import extends AbstractCommand {

    @Override
    public void execute(Options opts) throws Exception {

	if (opts.is("prop")) {
	    NodeKeyValue path = new NodeKeyValue(opts);
	    if (!path.isNode()) {
		throw new UsageException();
	    }
	    Properties prop = new Properties();
	    prop.load(System.in);
	    Preferences node = path.node();
	    for (String key : prop.stringPropertyNames()) {
		node.put(key, prop.getProperty(key, ""));
	    }
	    node.flush();
	} else {
	    Preferences.importPreferences(System.in);
	}

	// Preferences root;
	// if (opts.isOpt('s')) {
	// root = systemRoot;
	// } else if (opts.isOpt('u')) {
	// root = userRoot;
	// } else {
	// throw new UsageException();
	// }
	//
	// NodeKeyValue path = new NodeKeyValue(args);
	// if (path.node != null) {
	// root = root.node(path.node);
	// }
	// BufferedReader br = new BufferedReader(new
	// InputStreamReader(System.in));
	// String line;
	// while ((line = br.readLine()) != null) {
	// String[] split = line.split("\\s", 2);
	// if (split.length == 2) {
	// root.put(split[0], split[1]);
	// }
	// }
	// root.flush();
    }
}