package me.shakiba.preferd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Options {

    private Map<String, Opt> map = new HashMap<String, Opt>();
    private List<Opt> list = new ArrayList<Opt>();

    public void read(LinkedList<String> args, List<String> valueArgs) {
	while (!args.isEmpty()) {
	    String arg = args.poll();
	    if (arg.charAt(0) != '-') {
		continue;
	    }
	    Opt last = null;
	    if (arg.charAt(1) == '-') {
		if (arg.length() > 2) {
		    last = add(arg.substring(2));
		}
	    } else {
		for (char opt : arg.substring(1).toCharArray()) {
		    last = add(String.valueOf(opt));
		}
	    }
	    if (last != null && valueArgs.contains(last.getKey())
		    && !args.isEmpty()) {
		last.setValue(args.poll());
	    }
	}
    }

    private Opt add(String key) {
	Opt opt = new Opt(key);
	map.put(key, opt);
	list.add(opt);
	return opt;
    }

    public String getOpt(String opt) {
	return this.map.get(opt).getValue();
    }

    public List<Opt> getList() {
	return list;
    }

    /**
     * Do NOT prefix with - or --
     */
    public boolean any(String... opts) {
	for (String opt : opts) {
	    if (is(opt)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Do NOT prefix with - or --
     */
    public boolean all(String... opts) {
	for (String opt : opts) {
	    if (!is(opt)) {
		return false;
	    }
	}
	return true;
    }

    public boolean is(String opt) {
	return this.map.get(opt) != null;
    }

    class Opt {
	private String key;
	private String value;
	private boolean used;

	public Opt(String key) {
	    this.key = key;
	}

	public Opt(String key, String value) {
	    this.key = key;
	    this.value = value;
	}

	public void setValue(String value) {
	    this.value = value;
	}

	public String getKey() {
	    return key;
	}

	public boolean is(String opt) {
	    return !used && key.equals(opt);
	}

	public String getValue() {
	    return value;
	}

	@Override
	public String toString() {
	    return String.format("(%s:%s)", key, value);
	}

	public void used() {
	    this.used = true;
	}

	public boolean isUsed() {
	    return used;
	}
    }

    @Override
    public String toString() {
	return map + "\n" + list;
    }
}