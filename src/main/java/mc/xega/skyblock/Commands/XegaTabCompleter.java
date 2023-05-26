package mc.xega.skyblock.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class XegaTabCompleter implements TabCompleter {

    List<String> ops = new ArrayList<String>();

    List<String> ents = new ArrayList<String>();

    List<String> bosses = new ArrayList<String>();

    List<String> bpops = new ArrayList<String>();


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (ops.isEmpty()) {
            ops.add("summon");
            ops.add("bosspoints");
        }
        if (ents.isEmpty()) {
            ents.add("boss");
            ents.add("entity");
        }
        if (bosses.isEmpty()) {
            bosses.add("sk");
            bosses.add("scorch");
        }
        if (bpops.isEmpty()) {
            bpops.add("add");
            bpops.add("remove");
            bpops.add("list");
        }

        List<String> ops1 = new ArrayList<String>();

        switch (args.length) {
            case 1:
                for (String op: ops) {
                    if (op.toLowerCase().startsWith(args[0].toLowerCase())) {
                        ops1.add(op);
                    }
                }
                break;
            case 2:
                switch (args[0]) {
                    case "summon":
                        for (String op: ents) {
                            if (op.toLowerCase().startsWith(args[1].toLowerCase())) {
                                ops1.add(op);
                            }
                        }
                        break;
                    case "bosspoints":
                        for (String op: bpops) {
                            if (op.toLowerCase().startsWith(args[1].toLowerCase())) {
                                ops1.add(op);
                            }
                        }
                }
            case 3:
                switch (args[1]) {
                    case "boss":
                    case "add":
                        for (String op: bosses) {
                            if (op.toLowerCase().startsWith(args[2].toLowerCase())) {
                                ops1.add(op);
                            }
                        }
                        break;
                }

        }
        return ops1;
    }
}
