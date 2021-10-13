package pgdp.robot;

import java.util.*;
import java.util.function.Function;

public class Robot {
    /// Attributes
    //final attributes
    private final String name;
    private final double size;

    //internal states
    private Position position = new Position();
    private double direction;
    private World world;
    private List<Memory<?>> memory = new ArrayList<>();
    private List<Sensor<?>> sensors = new ArrayList<>();
    private Queue<Command> todo = new LinkedList<>();
    private Function<Robot, List<Command>> program;

    /// Methods
    public Robot(String name, double direction, double size) {
        this.name = name;
        this.direction = direction;
        this.size = Math.min(Math.max(0.5, size), 1);
    }

    public <T> Memory<T> createMemory(Memory<T> newMemory) {
        this.memory.add(newMemory);
        return newMemory;
    }

    public String memoryToString() {
        StringBuilder sb = new StringBuilder();
        for (Memory<?> m : this.memory) {
            sb.append(String.format("[%s]", m.toString()));
        }
        return sb.toString();
    }

    public void attachSensor(Sensor<?> sensor) {
        this.sensors.add(sensor);
        sensor.owner = this;
    }

    private void sense() {
        for (Sensor<?> s : this.sensors) {
            this.callProcessor(s);
        }
    }

    private <T> void callProcessor(Sensor<T> s) {
        s.processor.accept(s.getData());
    }

    public void setProgram(Function<Robot, List<Command>> newProgram) {
        this.program = newProgram;
    }

    private void think() {
        todo.addAll(this.program.apply(this));
    }

    private void act() {
        boolean result = true;
        while (result && !this.todo.isEmpty()) {
            Command cmd = this.todo.poll();
            result = cmd.execute(this);
        }
    }

    public void work() {
        if (this.todo.isEmpty()) {
            sense();
            think();
        }
        act();
    }


    /// Pre-programmed Commands
    public boolean go(double distance) {
        //step can be negative if the penguin walks backwards
        double sign = Math.signum(distance);
        distance = Math.abs(distance);
        //penguin walks, each step being 0.2m
        while (distance > 0) {
            position.moveBy(sign * Math.min(distance, 0.2), direction);
            world.resolveCollision(this, position);
            distance -= 0.2;
        }
        return true;
    }

    public boolean turnBy(double deltaDirection) {
        direction += deltaDirection;
        return true;
    }

    public boolean turnTo(double newDirection) {
        direction = newDirection;
        return true;
    }

    public boolean say(String text) {
        world.say(this, text);
        return true;
    }

    public boolean paintWorld(Position pos, char blockType) {
        world.setTerrain(pos, blockType);
        return true;
    }


    /// Getters and Setters
    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public Position getPosition() {
        return new Position(position);
    }

    public double getDirection() {
        return direction;
    }

    public World getWorld() {
        return world;
    }

    public void spawnInWorld(World world, char spawnMarker) {
        this.world = world;
        this.position = new Position(world.spawnRobotAt(this, spawnMarker));
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "\"%s\" position=%s direction=%.2f°", name, position, Math.toDegrees(direction));
    }
}
