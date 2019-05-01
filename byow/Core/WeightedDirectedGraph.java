package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * A very simple (and literal) example of an AStarGraph.
 * Created by hug.
 */
public class WeightedDirectedGraph implements AStarGraph<Position> {
    /* Represents the list of edges from a SINGLE vertex. */
    private class EdgeList {
        private List<WeightedEdge<Position>> list;

        private EdgeList(Position p) {
            addEdgesOfTile(p, 1);
        }

        public void addEdgesOfTile(Position p, double weight) {
            TETile pu = world.getTeTile()[p.x()][p.y() + 1];
            TETile pd = world.getTeTile()[p.x()][p.y() - 1];
            TETile pl = world.getTeTile()[p.x() - 1][p.y()];
            TETile pr = world.getTeTile()[p.x() + 1][p.y()];

            if (pu.equals(Tileset.FLOOR)) {
                list.add(new WeightedEdge<>(p, new Position(p.x(), p.y() + 1), weight));
            }

            if (pd.equals(Tileset.FLOOR)) {
                list.add(new WeightedEdge<>(p, new Position(p.x(), p.y() - 1), weight));
            }

            if (pl.equals(Tileset.FLOOR)) {
                list.add(new WeightedEdge<>(p, new Position(p.x() - 1, p.y()), weight));
            }

            if (pr.equals(Tileset.FLOOR)) {
                list.add(new WeightedEdge<>(p, new Position(p.x() + 1, p.y()), weight));
            }

        }
    }

    private ArrayList<EdgeList> adj;
    private HashMap<Position, Integer> mapping;
    private WorldGenerator world;


    public WeightedDirectedGraph(WorldGenerator w, int width, int height) {
        adj = new ArrayList<>();
        world = w;
        int index = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (w.getTeTile()[i][j].equals(Tileset.FLOOR)) {
                    Position pos = new Position(i, j);
                    EdgeList vertex = new EdgeList(pos);
                    adj.add(vertex);
                    mapping.put(pos, index);
                    index += 1;
                }

            }

        }
    }

    @Override
    public List<WeightedEdge<Position>> neighbors(Position v) {
        return adj.get(mapping.get(v)).list;
    }

    /* Very crude heuristic that just returns the weight
       of the smallest edge out of vertex s.
     */
    @Override
    public double estimatedDistanceToGoal(Position p, Position goal) {
//        List<WeightedEdge<Position>> edges = neighbors(p);
//        double estimate = Double.POSITIVE_INFINITY;
//        for (WeightedEdge<Position> e : edges) {
//            if (e.weight() < estimate) {
//                estimate = e.weight();
//            }
//        }
//        return estimate;
        return 0;
    }



}
