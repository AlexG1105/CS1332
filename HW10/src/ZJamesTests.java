import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZJamesTests {

    
    private Graph<Integer> directedGraph;
    private Graph<Integer> emptyDirectedGraph;
    private Graph<Character> undirectedGraph;
    private Graph<Character> emptyUndirectedGraph;
    public static final int TIMEOUT = 200;

    @Before
    public void init() {
        directedGraph = createDirectedGraph();
        undirectedGraph = createUndirectedGraph();
        emptyDirectedGraph = createEmptyDirectedGraph();
        emptyUndirectedGraph = createEmptyUndirectedGraph();
    }

    
    
    @Test(timeout = TIMEOUT)
    public void bfsDirectedTest() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(
                new Vertex<Integer>(1), directedGraph);

        List<Vertex<Integer>> bfsExpected = new LinkedList<>();
        bfsExpected.add(new Vertex<Integer>(1));
        bfsExpected.add(new Vertex<Integer>(2));
        bfsExpected.add(new Vertex<Integer>(3));
        bfsExpected.add(new Vertex<Integer>(4));
        bfsExpected.add(new Vertex<Integer>(5));
        bfsExpected.add(new Vertex<Integer>(6));
        bfsExpected.add(new Vertex<Integer>(7));

        assertEquals(bfsExpected, bfsActual);
    }
    
    @Test
    public void bfsUndirectedTest() {
        List<Vertex<Character>> bfsActual1 = GraphAlgorithms.bfs(
                new Vertex<Character>('A'), undirectedGraph);

        List<Vertex<Character>> bfsExpected1 = new LinkedList<>();
        bfsExpected1.add(new Vertex<Character>('A'));
        bfsExpected1.add(new Vertex<Character>('B'));
        bfsExpected1.add(new Vertex<Character>('C'));
        bfsExpected1.add(new Vertex<Character>('D'));
        bfsExpected1.add(new Vertex<Character>('E'));
        bfsExpected1.add(new Vertex<Character>('F'));

        assertEquals(bfsExpected1, bfsActual1);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bfsNullStartTest() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(
                null, directedGraph);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bfsNullGraphTest() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(
                new Vertex<Integer>(1), null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void bfsNonExistantNodeGraphTest() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(
                new Vertex<Integer>(30), null);
    }
    
    @Test(timeout = TIMEOUT)
    public void dfsTest() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(
                new Vertex<Integer>(5), directedGraph);

        List<Vertex<Integer>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<Integer>(5));
        dfsExpected.add(new Vertex<Integer>(4));
        dfsExpected.add(new Vertex<Integer>(6));
        dfsExpected.add(new Vertex<Integer>(7));

        assertEquals(dfsExpected, dfsActual);
        
        List<Vertex<Integer>> dfsActual0 = GraphAlgorithms.dfs(
                new Vertex<Integer>(2), directedGraph);

        List<Vertex<Integer>> dfsExpected0 = new LinkedList<>();
        dfsExpected0.add(new Vertex<Integer>(2));

        assertEquals(dfsExpected0, dfsActual0);
    }
    
    @Test
    public void dfsUndirectedTest() {
        List<Vertex<Character>> bfsActual1 = GraphAlgorithms.dfs(
                new Vertex<Character>('A'), undirectedGraph);

        List<Vertex<Character>> bfsExpected1 = new LinkedList<>();
        bfsExpected1.add(new Vertex<Character>('A'));
        bfsExpected1.add(new Vertex<Character>('B'));
        bfsExpected1.add(new Vertex<Character>('E'));
        bfsExpected1.add(new Vertex<Character>('D'));
        bfsExpected1.add(new Vertex<Character>('C'));
        bfsExpected1.add(new Vertex<Character>('F'));

        assertEquals(bfsExpected1, bfsActual1);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void dfsNullStartTest() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(
                null, directedGraph);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void dfsNullGraphTest() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(
                new Vertex<Integer>(1), null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void dfsNonExistantNodeGraphTest() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(
                new Vertex<Integer>(30), null);
    }
    
    @Test(timeout = TIMEOUT)
    public void dijkstrasTest() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<Character>('D'), undirectedGraph);
        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 4);
        dijkExpected.put(new Vertex<>('B'), 4);
        dijkExpected.put(new Vertex<>('C'), 2);
        dijkExpected.put(new Vertex<>('D'), 0);
        dijkExpected.put(new Vertex<>('E'), 1);
        dijkExpected.put(new Vertex<>('F'), 7);

        assertEquals(dijkExpected, dijkActual);
        
        Map<Vertex<Character>, Integer> dijkActual1 = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), undirectedGraph);
        Map<Vertex<Character>, Integer> dijkExpected1 = new HashMap<>();
        dijkExpected1.put(new Vertex<>('A'), 0);
        dijkExpected1.put(new Vertex<>('B'), 7);
        dijkExpected1.put(new Vertex<>('C'), 5);
        dijkExpected1.put(new Vertex<>('D'), 4);
        dijkExpected1.put(new Vertex<>('E'), 5);
        dijkExpected1.put(new Vertex<>('F'), 11);

        assertEquals(dijkExpected1, dijkActual1);
        
        Map<Vertex<Character>, Integer> dijkActual2 = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), createUndirectedGraph2());
        Map<Vertex<Character>, Integer> dijkExpected2 = new HashMap<>();
        dijkExpected2.put(new Vertex<>('A'), 0);
        dijkExpected2.put(new Vertex<>('B'), 7);
        dijkExpected2.put(new Vertex<>('C'), Integer.MAX_VALUE);
        dijkExpected2.put(new Vertex<>('D'), Integer.MAX_VALUE);

        assertEquals(dijkExpected2, dijkActual2);
        
        Map<Vertex<Character>, Integer> dijkActual3 = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), createUndirectedGraph3());
        Map<Vertex<Character>, Integer> dijkExpected3 = new HashMap<>();
        dijkExpected3.put(new Vertex<>('A'), 0);

        assertEquals(dijkExpected3, dijkActual3);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void dijkstrasNullStartTest() {
        GraphAlgorithms.dijkstras(
                null, directedGraph);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void dijkstrasNullGraphTest() {
        GraphAlgorithms.dijkstras(
                new Vertex<Integer>(1), null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void dijkstrasNonExistantNodeGraphTest() {
        GraphAlgorithms.dijkstras(
                new Vertex<Integer>(30), null);
    }
    
    @Test (timeout = TIMEOUT)
    public void primsTest() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.prims(
            new Vertex<>('A'), undirectedGraph);
        Set<Edge<Character>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));

        assertEquals(edges, mstActual);
        
        Set<Edge<Character>> mstActual2 = GraphAlgorithms.prims(
                new Vertex<>('A'), createUndirectedGraph2());
        
        assertEquals(null, mstActual2);
        
        Set<Edge<Character>> mstActual3 = GraphAlgorithms.prims(
                new Vertex<>('A'), createUndirectedGraph3());
        Set<Edge<Character>> edges1 = new HashSet<>();
        assertEquals(edges1, mstActual3);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void primsNullStartTest() {
        GraphAlgorithms.prims(
                null, directedGraph);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void primsNullGraphTest() {
        GraphAlgorithms.prims(
                new Vertex<Integer>(1), null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void primsNonExistantNodeGraphTest() {
        GraphAlgorithms.prims(
                new Vertex<Integer>(30), null);
    }
    
    private Graph<Integer> createDirectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        for (int i = 1; i <= 7; i++) {
            vertices.add(new Vertex<Integer>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));

        return new Graph<Integer>(vertices, edges);
    }
    
    
    
    private Graph<Integer> createEmptyDirectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        Set<Edge<Integer>> edges = new LinkedHashSet<Edge<Integer>>();
        return new Graph<Integer>(vertices, edges);
    }

    private Graph<Character> createUndirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 7));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 7));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 8));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));

        return new Graph<Character>(vertices, edges);
    }
    
    private Graph<Character> createUndirectedGraph1() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        vertices.add(new Vertex<Character>('A'));
        vertices.add(new Vertex<Character>('B'));

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 7));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 7));

        return new Graph<Character>(vertices, edges);
    }
    
    private Graph<Character> createUndirectedGraph2() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        vertices.add(new Vertex<Character>('A'));
        vertices.add(new Vertex<Character>('B'));
        vertices.add(new Vertex<Character>('C'));
        vertices.add(new Vertex<Character>('D'));

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 7));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 7));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 7));

        return new Graph<Character>(vertices, edges);
    }
    
    private Graph<Character> createUndirectedGraph3() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        vertices.add(new Vertex<Character>('A'));
        return new Graph<Character>(vertices, edges);
    }
    
    private Graph<Character> createEmptyUndirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        return new Graph<Character>(vertices, edges);
    }

}
