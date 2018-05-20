package club.shmoke.tpplate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Christian
 */
public class TeleportPlate extends JavaPlugin implements Listener {

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("\247aTeleportPlate\2477 has been enabled.");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!(player.getLocation().getBlock().getType() == Material.GOLD_PLATE)) return;

        Location mat = player.getLocation().add(0, 1, 0);

        Location[] signs = {mat, mat.add(1, 0, 0), mat.add(-1, 0, 0), mat.add(0, 0, 1), mat.add(0, 0, -1)};

        for (Location loc : signs) {
            if (!(loc.getBlock().getState() instanceof Sign)) continue;

            Sign sign = (Sign) loc.getBlock().getState();

            Direction dir = Direction.NONE;

            switch (sign.getLine(1).toLowerCase()) {
                case "[up]":
                    dir = Direction.UP;
                    break;
                case "[down]":
                    dir = Direction.DOWN;
                    break;
                case "[north]":
                    dir = Direction.NORTH;
                    break;
                case "[east]":
                    dir = Direction.EAST;
                    break;
                case "[south]":
                    dir = Direction.SOUTH;
                    break;
                case "[west]":
                    dir = Direction.WEST;
                    break;
                default:
                    break;
            }

            if (dir == Direction.NONE) return;

            try {
                Integer blocks = Integer.parseInt(sign.getLine(2));
                int id = sign.getData().getData();
                boolean vert = dir == Direction.UP || dir == Direction.DOWN;
                boolean up = dir == Direction.UP, down = dir == Direction.DOWN, north = dir == Direction.NORTH, east = dir == Direction.EAST, south = dir == Direction.SOUTH, west = dir == Direction.WEST;

                player.teleport(player.getLocation().add(
                        east ? blocks : west ? -blocks : vert ? id == 4 ? 1 : id == 5 ? -1 : 0 : 0,
                        up ? blocks : down ? -blocks : 0,
                        south ? blocks : north ? -blocks : vert ? id == 2 ? 1 : id == 3 ? -1 : 0 : 0));

                return;
            } catch (Exception ex) {
            }
        }
    }

    private enum Direction {
        NORTH, EAST, SOUTH, WEST, UP, DOWN, NONE
    }
}
