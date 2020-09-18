package com.lots.lotswxxw.util;

import com.lots.lotswxxw.domain.vo.BaseTreeNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 树结构相关工具类
 *
 * @author lots
 * @date 16:55 2018/3/20
 */
public class TreeUtil {


    /**
     * description 用双重循环建树
     *
     * @param treeNodes 1 树节点列表, root根标志
     * @param root      2
     * @return java.util.List<T>
     */
    public static <T extends BaseTreeNode> List<T> buildTreeBy2Loop(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<>();
        for (T node : treeNodes) {
            if (root.equals(node.getParentId())) {
                trees.add(node);
            }

            for (T treeNode : treeNodes) {
                if (node.getId().equals(treeNode.getParentId())) {
                    if (node.getChildren() == null) {
                        node.setChildren(new ArrayList<>());
                    }
                    node.addChilren(treeNode);
                }
            }
        }
        return trees;
    }

    public static void main(String[] args) throws IOException {
        String url = "http://kaijiang.500.com/ssq.shtml";
        Document document = Jsoup.connect(url).get();
        String body = document.getElementsByClass("ball_red").text();
        String[] s = body.split(" ");
        String span_right = document.getElementsByClass("span_right").text();
        String redNumber = String.join(" , ", s);
        Integer blueNumber = Integer.parseInt(document.getElementsByClass("ball_blue").text());
        String getdate = span_right.substring(span_right.lastIndexOf("开奖日期：") + 5).substring(0, span_right.indexOf(" 兑奖截止") - 5);
        System.out.println(redNumber + "-" + blueNumber + "-" + getdate);
    }

}
