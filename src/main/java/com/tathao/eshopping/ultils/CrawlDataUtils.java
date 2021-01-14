package com.tathao.eshopping.ultils;

import com.tathao.eshopping.model.dto.ProductDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrawlDataUtils {

    public static List<ProductDTO> crawlData(String url) throws IOException {
        List<ProductDTO> result = new ArrayList<ProductDTO>();
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass("product-item");
        for(Element element : elements) {
            String image = element.select("a.product-image > img").attr("src");
            String productName = element.select("div.product-detail > .product-name > a").text();

            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(productName);
            productDTO.setImage(image);

            result.add(productDTO);
        }
        return result;
    }

    public static void main(String...strings) {
        try {
            List<ProductDTO> products = CrawlDataUtils.crawlData("https://canifa.com/nam/danh-muc-san-pham.html");
            for(ProductDTO dto : products) {
                System.out.println("product name " + dto.getName());
                System.out.println("product iamge " + dto.getImage());
                System.out.println("====================");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
