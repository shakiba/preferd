package x.preferd.cmd;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import x.preferd.Main;
import x.preferd.Options;


public class Help extends AbstractCommand {

    @Override
    public void execute(Options opts) throws Exception {
	BufferedReader reader = new BufferedReader(new InputStreamReader(
		Main.class.getResourceAsStream("help")));
	String line;
	while ((line = reader.readLine()) != null) {
	    System.out.println(line);
	}
    }
}