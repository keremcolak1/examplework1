package pgdp.robot;

import java.util.ArrayList;
import java.util.List;

public class User {
    public static void main(String[] args) {

        Robot panicPenguin = new Robot("Panic!", 0, 0.5);
        //create memory
        Memory<Character> terrain = panicPenguin.createMemory(new Memory<>("terrain", ' '));
        //create and attach sensors
        panicPenguin.attachSensor(new TerrainSensor().setProcessor(terrain::set));
        //program the robot

        panicPenguin.setProgram(robot -> {
            List<Command> commands = new ArrayList<>();
            commands.add(r -> r.say(terrain.get().toString()));
            commands.add(r -> r.turnBy(Math.toRadians(5)));
            commands.add(r -> r.go(0.1));
            return commands;
        });


        /*World world = new World("""
                #######
                #  0  #
                #Don't#
                #PANIC#
                #     #
                #######""");

        panicPenguin.spawnInWorld(world, '0');
        world.run();*/

        /*Robot lineFollowerPenguin = makeLineFollower();

        World world = new World("""
                ################
                #v<#           #
                #v^#   #v<< #  #
                #v^<<<<<<0^ #  #
                #v   # >>>^#   #
                #v###  ^#     ##
                #>>>>>>^       #
                ################""");

        lineFollowerPenguin.spawnInWorld(world, '0');
        world.run();  */


        Robot mazeRunnerRobot = makeMazeRunner();
        World world = new World("""
                ##############
                #  0   #   ##  $
                #  #  ##   #   #
                ####   # ### ###
                #      #       #
                #  #      ##   #
                ################""");
        mazeRunnerRobot.spawnInWorld(world, '0');
        world.run();
    }

    public static Robot makeLineFollower() {
        Robot lineFollowerPenguin = new Robot("Panic!", 0, 0.5);
        //create memory
        Memory<Character> terrain = lineFollowerPenguin.createMemory(new Memory<>("terrain", ' '));
        //create and attach sensors
        lineFollowerPenguin.attachSensor(new TerrainSensor().setProcessor(terrain::set));
        //program the robot
        lineFollowerPenguin.setProgram(robot -> {
            List<Command> commands = new ArrayList<>();
            commands.add(r -> r.say(terrain.get().toString()));
            commands.add(r ->
            {
                switch (terrain.get()) {
                    case '>':
                        return r.turnTo(Math.toRadians(0));
                    case '<':
                        return r.turnTo(Math.toRadians(180));
                    case 'v':
                        return r.turnTo(Math.toRadians(90));
                    case '^':
                        return r.turnTo(Math.toRadians(-90));
                    default:
                        return true;
                }
            });
            commands.add(r -> r.go(0.2));
            return commands;
        });

        return lineFollowerPenguin;
    }

    public static Robot makeMazeRunner() {
        Robot mazeRunnerPenguin = new Robot("Panic!", 0, 0.5);
        //create memory
        Memory<Character> terrain = mazeRunnerPenguin.createMemory(new Memory<>("terrain", ' '));
        //create and attach sensors
        mazeRunnerPenguin.attachSensor(new TerrainSensor().setProcessor(terrain::set));
        //program the robot
        mazeRunnerPenguin.setProgram(robot -> {
            List<Command> commands = new ArrayList<>();
            commands.add(r -> r.say(terrain.get().toString()));

            commands.add(r ->
            {
                Position pos = r.getPosition();
                double radius = robot.getSize() / 2;
                // edge vertical axis
                boolean xEdge = Math.ceil(pos.x - radius) == Math.floor(pos.x + radius);
                //edge horizontal axis
                boolean yEdge = Math.ceil(pos.y - radius) == Math.floor(pos.y + radius);

                Position intersection = new Position(
                        xEdge ? Math.ceil(pos.x - radius) : pos.x,
                        yEdge ? Math.ceil(pos.y - radius) : pos.y);
                if (xEdge ^ yEdge) {
                    boolean blocked = (r.getWorld().getTerrain(intersection) == '#')
                            ^ (r.getWorld().getTerrain(intersection.x + (xEdge ? -1 : 0), intersection.y + (yEdge ? -1 : 0)) == '#');
                    if (blocked) {

                        return r.turnBy(180);
                    }
                }
                return true;
            });
            commands.add(r -> r.go(0.2));
            return commands;
        });

        return mazeRunnerPenguin;
    }
}
