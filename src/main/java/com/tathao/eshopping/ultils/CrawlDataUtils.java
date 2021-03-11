package com.tathao.eshopping.ultils;

import com.tathao.eshopping.config.ApplicationContextConfig;
import com.tathao.eshopping.config.DataSourceInitializer;
import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.model.entity.ProductEntity;
import com.tathao.eshopping.model.entity.ProductSkuEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.TransactionManager;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CrawlDataUtils {

    private static SessionFactory sessionFactory;

    public static List<ProductEntity> crawlData(String url) throws IOException {
        List<ProductEntity> result = new ArrayList<ProductEntity>();
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass("product-item");
        for(Element element : elements) {
            String image = element.select("a.product-image > img").attr("src");
            String productName = element.select("div.product-detail > .product-name > a").text();
            String priceString = element.select("div.product-detail > .price-box > p.price").text().replaceAll("\\D", "");

            ProductEntity product = new ProductEntity();
            product.setName(productName);
            product.setImage(image);
            product.setBrandId(Long.valueOf(1));
            product.setCatgroupId(Long.valueOf(1));
            product.setCode(CommonUtils.generateCode());
            product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            product.setStatus(true);

            List<ProductSkuEntity> skuEntites = new ArrayList<ProductSkuEntity>();
            for(int i = 28; i <= 39; i++) {
                ProductSkuEntity sku = new ProductSkuEntity();
                sku.setBarCode(CommonUtils.generateCode());
                sku.setImage(product.getImage());
                sku.setOriginalPrice(Double.parseDouble(priceString));
                sku.setSalePrice(Double.parseDouble(priceString));
                sku.setProduct(product);
                sku.setSkuCode(CommonUtils.generateCode());
                sku.setStatus(true);
                sku.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                sku.setUnit("Cái");
                sku.setTitle("Size " + i);
                skuEntites.add(sku);
            }

            product.setProductSkus(skuEntites);
            result.add(product);
        }
        return result;
    }

    public static void save(ProductEntity product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
//            List<ProductSkuEntity> productSkus = product.getProductSkus();

            session.save(product);
            transaction.commit();
            session.close();
            System.out.println("Product saved");

//            saveMoreProductSku(productSkus);

        } catch (HibernateException e) {
            System.out.println("####Error: " + e.getCause());
            transaction.rollback();
        }
    }

    public static void saveMoreProduct(List<ProductEntity> products) {
        System.out.println("Begin save product...");
        for(ProductEntity product : products) {
            save(product);
        }
    }

    public static void saveSku(ProductSkuEntity skuEntity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(skuEntity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("####Error: " + e.getCause());
        }

    }

    public static void saveMoreProductSku(List<ProductSkuEntity> skus) {
        System.out.println("Begin save product sku...");
        for(ProductSkuEntity skuEntity : skus) {
            saveSku(skuEntity);
        }
    }

    public static void main(String...strings) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DataSourceInitializer.class);
        sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");

        try {
            List<ProductEntity> products = CrawlDataUtils.crawlData("https://canifa.com/nam/danh-muc-san-pham.html");
            saveMoreProduct(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
