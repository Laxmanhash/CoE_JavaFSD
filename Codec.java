public class Codec {
    
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preorder(root, sb);
        return sb.toString();
    }

    private void preorder(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null,");
            return;
        }
        sb.append(node.val).append(",");
        preorder(node.left, sb);
        preorder(node.right, sb);
    }

    public TreeNode deserialize(String data) {
        String[] nodes = data.split(",");
        int[] index = {0};
        return buildTree(nodes, index);
    }

    private TreeNode buildTree(String[] nodes, int[] index) {
        if (index[0] >= nodes.length || nodes[index[0]].equals("null")) {
            index[0]++;
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(nodes[index[0]++]));
        node.left = buildTree(nodes, index);
        node.right = buildTree(nodes, index);
        return node;
    }

    public static void main(String[] args) {
        Codec codec = new Codec();
        TreeNode root = codec.new TreeNode(1);
        root.left = codec.new TreeNode(2);
        root.right = codec.new TreeNode(3);
        root.left.left = codec.new TreeNode(4);
        root.left.right = codec.new TreeNode(5);

        String serializedData = codec.serialize(root);
        System.out.println("Serialized Data: " + serializedData);

        TreeNode deserializedTree = codec.deserialize(serializedData);
        System.out.println("Deserialization Complete");
    }
}
