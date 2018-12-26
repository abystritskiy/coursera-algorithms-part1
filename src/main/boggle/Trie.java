public class Trie {
    private TNode root;
    private int n;

    public Trie() {
        this.root = new TNode();
    }

    public int size() {
        return n;
    }

    private int getCharIndex(char c) {
        int val = c - 65;
        return val;
    }

    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    public String get(String key) {
        Trie.TNode prefixNode = prefixNode(key);
        if (prefixNode == null) {
            return "";
        }
        return prefixNode.val;
    }

    public void put(String key, String val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        root = put(root, key, val, 0);
    }

    private TNode put(TNode x, String key, String val, int d) {
        if (x == null) x = new TNode();
        if (d == key.length()) {
            if (x.val == null) n++;
            x.val = val;
            return x;
        }
        int c = getCharIndex(key.charAt(d));
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public TNode prefixNode(String prefix) {
        Trie.TNode x = root;
        int depth = 0;

        while (depth < prefix.length()) {
            char chr = prefix.charAt(depth);
            int idx = getCharIndex(chr);
            x = x.next[idx];

            if (x == null) {
                return null;
            }
            depth++;
        }
        return x;
    }


    private class TNode {
        private String val;
        private TNode[] next = new TNode[26];
    }
}
