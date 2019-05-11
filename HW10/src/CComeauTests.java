import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * All these example graphs were pulled from the slides posted on canvas
 *
 * @author Charlie Comeau
 * @version 1.0
 */
public class CComeauTests {

    private Graph<Integer> directedGraph;
    private Graph<Character> directedGraph2;
    private Graph<Character> undirectedGraph;
    private Graph<Character> bfsGraph1;
    private Graph<Character> dijkstraGraph2;
    private Graph<Character> dijkstraGraph3;
    private Graph<Character> dijkstraGraph4;
    private Graph<Character> primGraph;
    public static final int TIMEOUT = 200;

    @Before
    public void init() {
        directedGraph = createDirectedGraph();
        directedGraph2 = createdirectedGraph2();
        undirectedGraph = createUndirectedGraph();
        bfsGraph1 = createBFSGraph1();
        dijkstraGraph2 = createDijkstraGraph2();
        dijkstraGraph3 = createDijkstraGraph3();
        dijkstraGraph4 = createDijkstraGraph4();
        primGraph = createPrimGraph();
    }

    /**
     * Creates a directed graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
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

    /**
     * Taken from second example of bfs slides, reused for recursive dfs
     * @return completed graph
     */
    private Graph<Character> createdirectedGraph2() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 73; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 4));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 4));

        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 8));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 8));

        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 7));

        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 9));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 9));

        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 10));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 10));

        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 2));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 2));

        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 1));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 1));

        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('A'), 8));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('H'), 8));

        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('B'), 11));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('H'), 11));

        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('F'), 4));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('C'), 4));

        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 14));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 14));

        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('I'), 7));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('H'), 7));

        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('I'), 2));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('C'), 2));

        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('I'), 6));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('G'), 6));

        return new Graph<Character>(vertices, edges);
    }

    /**
     * Taken from first example of bfs graph slides
     * @return completed graph
     */
    private Graph<Character> createBFSGraph1() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 69; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 1));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 1));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 1));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 1));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 1));


        return new Graph<Character>(vertices, edges);

    }


    private Graph<Character> createDijkstraGraph2() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 72; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 5));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 5));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 3));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 2));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 2));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('G'), 5));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('E'), 5));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('G'), 9));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('D'), 9));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 1));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('H'), 7));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 7));

        return new Graph<Character>(vertices, edges);
    }

    private Graph<Character> createDijkstraGraph3() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 69; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 1));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 3));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 3));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 0));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 0));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 4));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 4));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 4));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 1));

        return new Graph<Character>(vertices, edges);
    }

    private Graph<Character> createDijkstraGraph4() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 69; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 8));
        return new Graph<Character>(vertices, edges);
    }

    private Graph<Character> createPrimGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 72; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }
        Set<Edge<Character>> edges = new LinkedHashSet<>();

        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 6));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 8));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 8));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 8));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 1));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 7));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 7));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 7));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 2));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('F'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('C'), 1));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 5));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 5));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 3));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 3));


        return new Graph<Character>(vertices, edges);
    }

    /**
     * Creates an undirected graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
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

    @Test(timeout = TIMEOUT)
    public void testBFS() {
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

    @Test(timeout = TIMEOUT)
    public void testbfs1() {
        List<Vertex<Character>> bfsActual = GraphAlgorithms.bfs(
                new Vertex<Character>('A'), bfsGraph1);
        List<Vertex<Character>> bfsExpected = new LinkedList<>();
        bfsExpected.add(new Vertex<>('A'));
        bfsExpected.add(new Vertex<>('B'));
        bfsExpected.add(new Vertex<>('C'));
        bfsExpected.add(new Vertex<>('E'));
        bfsExpected.add(new Vertex<>('D'));

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testbfs2() {
        List<Vertex<Character>> bfsActual = GraphAlgorithms.bfs(
                new Vertex<Character>('A'), directedGraph2);
        List<Vertex<Character>> bfsExpected = new LinkedList<>();
        bfsExpected.add(new Vertex<>('A'));
        bfsExpected.add(new Vertex<>('B'));
        bfsExpected.add(new Vertex<>('H'));
        bfsExpected.add(new Vertex<>('C'));
        bfsExpected.add(new Vertex<>('G'));
        bfsExpected.add(new Vertex<>('I'));
        bfsExpected.add(new Vertex<>('D'));
        bfsExpected.add(new Vertex<>('F'));
        bfsExpected.add(new Vertex<>('E'));

        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFS() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(
                new Vertex<Integer>(5), directedGraph);

        List<Vertex<Integer>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<Integer>(5));
        dfsExpected.add(new Vertex<Integer>(4));
        dfsExpected.add(new Vertex<Integer>(6));
        dfsExpected.add(new Vertex<Integer>(7));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testdfs2() {
        List<Vertex<Character>> dfsActual = GraphAlgorithms.dfs(
                new Vertex<Character>('A'), directedGraph2);
        List<Vertex<Character>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<>('A'));
        dfsExpected.add(new Vertex<>('B'));
        dfsExpected.add(new Vertex<>('C'));
        dfsExpected.add(new Vertex<>('D'));
        dfsExpected.add(new Vertex<>('E'));
        dfsExpected.add(new Vertex<>('F'));
        dfsExpected.add(new Vertex<>('G'));
        dfsExpected.add(new Vertex<>('H'));
        dfsExpected.add(new Vertex<>('I'));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras() {
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
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras2() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), dijkstraGraph2);
        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);
        dijkExpected.put(new Vertex<>('C'), 2);
        dijkExpected.put(new Vertex<>('D'), 4);
        dijkExpected.put(new Vertex<>('B'), 5);
        dijkExpected.put(new Vertex<>('E'), 7);
        dijkExpected.put(new Vertex<>('F'), 9);
        dijkExpected.put(new Vertex<>('G'), 12);
        dijkExpected.put(new Vertex<>('H'), 13);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras3() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), dijkstraGraph3);
        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);
        dijkExpected.put(new Vertex<>('B'), 1);
        dijkExpected.put(new Vertex<>('C'), 2);
        dijkExpected.put(new Vertex<>('D'), 2);
        dijkExpected.put(new Vertex<>('E'), 6);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras4() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), dijkstraGraph4);
        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);
        dijkExpected.put(new Vertex<>('B'), 1);
        dijkExpected.put(new Vertex<>('C'), 2);
        dijkExpected.put(new Vertex<>('D'), 5);
        dijkExpected.put(new Vertex<>('E'), Integer.MAX_VALUE); // unconnected vertex

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras5() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<Character>('A'), directedGraph2);
        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);
        dijkExpected.put(new Vertex<>('B'), 4);
        dijkExpected.put(new Vertex<>('C'), 12);
        dijkExpected.put(new Vertex<>('D'), 19);
        dijkExpected.put(new Vertex<>('E'), 21);
        dijkExpected.put(new Vertex<>('F'), 11);
        dijkExpected.put(new Vertex<>('G'), 9);
        dijkExpected.put(new Vertex<>('H'), 8);
        dijkExpected.put(new Vertex<>('I'), 14);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test(timeout = TIMEOUT)
    public void testPrims() {
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
    }

    @Test(timeout = TIMEOUT)
    public void testPrims2() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.prims(
                new Vertex<>('B'), directedGraph2);
        Set<Edge<Character>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 4));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 8));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('I'), 2));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('C'), 4));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('F'), 4));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 2));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 2));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 1));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 1));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 9));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 9));

        assertEquals(edges, mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testPrims3() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.prims(
                new Vertex<>('H'), primGraph);
        Set<Edge<Character>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 6));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 8));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 7));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 7));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('C'), 1));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('F'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 5));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 5));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 3));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 3));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 1));

        assertEquals(edges, mstActual);
    }

    @Test(timeout = TIMEOUT)
    public void testPrims4() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.prims(
                new Vertex<>('A'), dijkstraGraph4);
        assertNull(mstActual);
        // test for disconnected graph case
    }


    @AfterClass
    public static void finished() {
        System.out.println("\n" +
                "                 .\"-,.__\n" +
                "                 `.     `.  ,\n" +
                "              .--'  .._,'\"-' `.\n" +
                "             .    .'         `'\n" +
                "             `.   /          ,'          DRACARYSSS!!!!\n" +
                "               `  '--.   ,-\"'\n" +
                "                `\"`   |  \\\n" +
                "                   -. \\, |\n" +
                "                    `--Y.'      ___.\n" +
                "                         \\     L._, \\\n" +
                "               _.,        `.   <  <\\                _\n" +
                "             ,' '           `, `.   | \\            ( `\n" +
                "          ../, `.            `  |    .\\`.           \\ \\_\n" +
                "         ,' ,..  .           _.,'    ||\\l            )  '\".\n" +
                "        , ,'   \\           ,'.-.`-._,'  |           .  _._`.\n" +
                "      ,' /      \\ \\        `' ' `--/   | \\          / /   ..\\\n" +
                "    .'  /        \\ .         |\\__ - _ ,'` `        / /     `.`.\n" +
                "    |  '          ..         `-...-\"  |  `-'      / /        . `.\n" +
                "    | /           |L__           |    |          / /          `. `.\n" +
                "   , /            .   .          |    |         / /             ` `\n" +
                "  / /          ,. ,`._ `-_       |    |  _   ,-' /               ` \\\n" +
                " / .           \\\"`_/. `-_ \\_,.  ,'    +-' `-'  _,        ..,-.    \\`.\n" +
                ".  '         .-f    ,'   `    '.       \\__.---'     _   .'   '     \\ \\\n" +
                "' /          `.'    l     .' /          \\..      ,_|/   `.  ,'`     L`\n" +
                "|'      _.-\"\"` `.    \\ _,'  `            \\ `.___`.'\"`-.  , |   |    | \\\n" +
                "||    ,'      `. `.   '       _,...._        `  |    `/ '  |   '     .|\n" +
                "||  ,'          `. ;.,.---' ,'       `.   `.. `-'  .-' /_ .'    ;_   ||\n" +
                "|| '              V      / /           `   | `   ,'   ,' '.    !  `. ||\n" +
                "||/            _,-------7 '              . |  `-'    l         /    `||\n" +
                ". |          ,' .-   ,' ||               | .-.        `.      .'     ||\n" +
                " `'        ,'    `\".'    |               |    `.        '. -.'       `'\n" +
                "          /      ,'      |               |,'    \\-.._,.'/'\n" +
                "          .     /        .               .       \\    .''\n" +
                "        .`.    |         `.             /         :_,'.'\n" +
                "          \\ `...\\   _     ,'-.        .'         /_.-'\n" +
                "           `-.__ `,  `'   .  _.>----''.  _  __  /\n" +
                "                .'        /\"'          |  \"'   '_\n" +
                "               /_|.-'\\ ,\".             '.'`__'-( \\\n" +
                "                 / ,\"'\"\\,'               `/  `-.|\"");
    }

}
