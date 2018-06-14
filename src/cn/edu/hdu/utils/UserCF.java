package cn.edu.hdu.utils;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Enzo
 * @Description: 基于用户推荐测试
 * @Date: Create in 14:01 2018/6/5
 * @Params: 
 * @Modified by: 
 */   

public class UserCF {

    private final static int NEIGHBORHOOD_NUM = 2;
    private final static int RECOMMEND_NUM = 2;

    public static void main(String[] args) throws IOException, TasteException {
//        String name = "E:/bangbang/ht/ratings.csv";
        String name = "E:/bangbang/ht/random.txt";
        // 创建数据模型
        DataModel model = new FileDataModel(new File(name));
        // 计算相似度 皮尔逊算法
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        //计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, similarity, model);
        //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于用户的协同过滤推荐
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        // 用户ID等于1的用户推荐RECOMMEND_NUM部电影
        List<RecommendedItem> recommendations = recommender.recommend(359, RECOMMEND_NUM);
        for(RecommendedItem recommendation : recommendations) {
            System.out.println(recommendation);
        }

    }

}
