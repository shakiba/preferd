package me.shakiba.preferd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import me.shakiba.preferd.cmd.AbstractCommand;
import me.shakiba.preferd.cmd.Copy;
import me.shakiba.preferd.cmd.Export;
import me.shakiba.preferd.cmd.Get;
import me.shakiba.preferd.cmd.Help;
import me.shakiba.preferd.cmd.Import;
import me.shakiba.preferd.cmd.List;
import me.shakiba.preferd.cmd.Move;
import me.shakiba.preferd.cmd.Put;
import me.shakiba.preferd.cmd.Remove;



public class Main {

    public static void main(String[] args0) throws Exception {
	LinkedList<String> args = new LinkedList<String>(Arrays.asList(args0));

	// should be before opts.read
	AbstractCommand cmd = cmds.get(args.poll());
	Options opts = new Options();
	opts.read(args, Arrays.asList("u s k v".split("\\s")));
	try {
	    if (cmd != null) {
		cmd.execute(opts);
	    } else {
		new Help().execute(opts);
		// throw new UsageException();
	    }
	} catch (UsageException e) {
	    handle(e, opts.is("e"));
	} catch (ExecutionError e) {
	    handle(e, opts.is("e"));
	}
    }

    private static void handle(Exception e, boolean full) {
	if (full) {
	    e.printStackTrace();
	} else {
	    System.out.println("ERROR: " + e.getMessage());
	    System.out.println("Use -e to see stack trace.");
	}
    }

    static Map<String, AbstractCommand> cmds = new HashMap<String, AbstractCommand>();
    static {

	cmds.put("ls", new List());
	cmds.put("list", cmds.get("ls"));

	cmds.put("get", new Get());

	cmds.put("mk", new Put());
	cmds.put("set", cmds.get("mk"));
	cmds.put("add", cmds.get("mk"));
	cmds.put("put", cmds.get("mk"));

	cmds.put("rm", new Remove());
	cmds.put("remove", cmds.get("rm"));
	cmds.put("delete", cmds.get("rm"));
	cmds.put("del", cmds.get("rm"));

	cmds.put("cp", new Copy());
	cmds.put("copy", cmds.get("cp"));

	cmds.put("mv", new Move());
	cmds.put("move", cmds.get("mv"));

	cmds.put("export", new Export());
	cmds.put("import", new Import());

	cmds.put("help", new Help());
    }
}