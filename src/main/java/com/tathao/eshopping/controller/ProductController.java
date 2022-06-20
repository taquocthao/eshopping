package com.tathao.eshopping.controller;

import com.tathao.eshopping.model.command.CatGroupCommand;
import com.tathao.eshopping.model.command.ProductCommand;
import com.tathao.eshopping.model.dto.CatGroupDTO;
import com.tathao.eshopping.model.dto.ProductDTO;
import com.tathao.eshopping.service.CatGroupService;
import com.tathao.eshopping.service.ProductService;
import com.tathao.eshopping.ultils.CoreConstants;
import com.tathao.eshopping.ultils.FileUtils;
import com.tathao.eshopping.ultils.RequestUtils;
import com.tathao.eshopping.ultils.WebConstants;
import com.tathao.eshopping.ultils.config.Config;
import com.tathao.eshopping.ultils.excel.ExcelUtils;
import com.tathao.eshopping.validator.ProductValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController extends ApplicationObjectSupport {

    private final Logger logger = Logger.getLogger(this.getClass());
    private static final String OUT_PATH_EXCEL = Config.getInstance().getProperty("output.file.root");

    @Autowired
    private ProductService productService;
    @Autowired
    private CatGroupService catGroupService;
    @Autowired
    private ProductValidator productValidator;


    @RequestMapping(value = "/product/{catGroup}/{productCode}/detail.html")
    public ModelAndView viewDetail(@PathVariable("catGroup") String catGroup, @PathVariable("productCode") String productCode) {
        ModelAndView mav = new ModelAndView("/shopper/productDetails");
        try {
            logger.info("view product detail - code: " + productCode + " - catGroup: " + catGroup);
            ProductDTO dto = productService.findByCodeAndFetchRelatedProducts(productCode);
            mav.addObject("product", dto);
        } catch (Exception e) {
             logger.error("view product detail error", e);
        }
        return mav;
    }

    @RequestMapping(value = "/ajax/product/search.json")
    @ResponseBody
    public Object searchProducts(@RequestParam(value = "query") String query) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", false);
        try {
            Map<String, Object> properties = new HashMap<>();
            properties.put("name", query);
            String whereClause = "A.status = true";
            Object[] resultObject = productService.findByProperties(properties, "name", CoreConstants.SORT_ASC, 0, CoreConstants.TEN_ITEMS, whereClause);
            if(Integer.parseInt(resultObject[0].toString()) > 0) {
                map.put("result", true);
            }
            map.put("products", resultObject[1]);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return map;
    }

    @RequestMapping(value = "/admin/product.html")
    public ModelAndView productManagement(@ModelAttribute(value = CoreConstants.FORM_MODEL_KEYS) ProductCommand command, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/admin/product/list");
        String crudaction = command.getCrudaction();
        try {
            if(!StringUtils.isEmpty(crudaction) && crudaction.equals(CoreConstants.FORM_ACTION_ACTIVE)) {
                productService.updateStatus(this.convertList(command.getCheckList()), true);
                mav.addObject(CoreConstants.ALTER, CoreConstants.TYPE_SUCCESS);
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("message.notify.active.product.success"));
            } else if(!StringUtils.isEmpty(crudaction) && crudaction.equals(CoreConstants.FORM_ACTION_DEACTIVE)) {
                productService.updateStatus(convertList(command.getCheckList()), false);
                mav.addObject(CoreConstants.ALTER, CoreConstants.TYPE_SUCCESS);
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("message.notify.dactive.product.success"));
            }

            executeSearchListProduct(command, request);
            referenceData4Admin(mav);
        } catch (Exception e) {
            if(!StringUtils.isEmpty(crudaction) && crudaction.equals(CoreConstants.FORM_ACTION_ACTIVE)) {
                mav.addObject(CoreConstants.ALTER, CoreConstants.TYPE_DANGER);
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("message.notify.active.product.failure"));
            } else if(!StringUtils.isEmpty(crudaction) && crudaction.equals(CoreConstants.FORM_ACTION_DEACTIVE)) {
                mav.addObject(CoreConstants.ALTER, CoreConstants.TYPE_DANGER);
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("message.notify.dactive.product.failure"));
            }
            logger.error("POSITION method productManagement - error ", e);
        }
        return mav;
    }

    @RequestMapping(value = "/admin/product/edit.html")
    public ModelAndView edit(@ModelAttribute(value = CoreConstants.FORM_MODEL_KEY) ProductCommand command, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/admin/product/edit");
        try {
            ProductDTO pojo = command.getPojo();
            String crudaction = command.getCrudaction();
            if(!StringUtils.isEmpty(crudaction) && WebConstants.INSERT_OR_UPDATE.equals(crudaction)) {
                productValidator.validate(command, result);
                if(!result.hasErrors()) {
                    if(pojo.getProductId() != null) { // update product
                        productService.update(pojo);
                        redirectAttributes.addFlashAttribute(CoreConstants.MESSAGE_RESPONSE,
                                this.getMessageSourceAccessor().getMessage("label.product.edit.successful"));
                    } else { // add new product
                        productService.add(pojo);
                        redirectAttributes.addFlashAttribute(CoreConstants.MESSAGE_RESPONSE,
                                this.getMessageSourceAccessor().getMessage("label.product.add.successful"));
                    }
                    redirectAttributes.addFlashAttribute(CoreConstants.ALTER, CoreConstants.TYPE_SUCCESS);
                    return new ModelAndView("redirect:/admin/product.html");
                }
            } else {
                if(pojo != null && !StringUtils.isEmpty(pojo.getCode())) {
                    pojo = productService.findByCode(pojo.getCode());
                    command.setPojo(pojo);
                }
            }
        } catch (Exception e) {
            logger.error("method edit - error:" , e);
            mav.addObject(WebConstants.ALER, WebConstants.TYPE_DANGER);
            mav.addObject(WebConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("label.error.occurred"));
        }
        referenceData4Admin(mav);
        return mav;
    }

    @RequestMapping(value = "/ajax/admin/product/upload/file.html", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam("imageProduct") MultipartFile multipartFile, HttpServletRequest request) {
        String pathFile = "";
        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhss");
            String fileName = "product_" + sdf.format(now) + "_" + multipartFile.getOriginalFilename();
            String originalImagePath =  Config.getInstance().getProperty("output.image.root");
            String productPath = WebConstants.ROOT_PRODUCT_UPLOAD_IMAGE;
            pathFile = originalImagePath + productPath;
            pathFile = FileUtils.uploadFile(pathFile, fileName, multipartFile);
        } catch (Exception e) {
            logger.error("method uploadFile error: " + e);
        }
        return pathFile;
    }

    @RequestMapping(value = "/ajax/admin/product/export.html")
    @ResponseBody
    public String exportExcelProduct(ProductCommand command, HttpServletRequest request, HttpServletResponse response) {
        String url = "";
        try {
            executeSearchListProduct(command, request);
            url = exportExcel(command, request, response);
        } catch (Exception e){
            logger.error(" method exportExcelCatGroup error:", e);
        }
        return url;
    }

    private void executeSearchListProduct(ProductCommand command, HttpServletRequest request) {
        Map<String, Object> buildProperties = buildProperties4AdminSearch(request, command);
        StringBuilder whereClause = new StringBuilder("1 = 1");
        if(command.getPojo() != null && command.getPojo().getStatus() != null) {
            whereClause.append("AND A.status = ").append(command.getPojo().getStatus());
        }
        Object[] result = productService.findByProperties(buildProperties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getTotalItems(), whereClause.toString());
        command.setListResult((List<ProductDTO>) result[1]);
        command.setTotalItems(Integer.parseInt(result[0].toString()));
    }

    private String exportExcel(ProductCommand command, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String templatePath = request.getSession().getServletContext().getRealPath("/file/template-export/product.xlsx");
        String fileName = "product_";
        List<Object[]> listValue = new ArrayList<>();
        for(ProductDTO productDTO : command.getListResult()) {
            Object[] item = new Object[8];
            item[0] = productDTO.getCatGroup().getName();
            item[1] = productDTO.getCode();
            item[2] = productDTO.getName();
            if(productDTO.getReferencePrice() != null) {
                Double lowestPrice = productDTO.getReferencePrice().getLowestPrice();
                Double highestPrice = productDTO.getReferencePrice().getHighestPrice();
                item[3] = new BigDecimal(lowestPrice).toPlainString();
                if(highestPrice != null) {
                    item[3] += ("~" +new BigDecimal(highestPrice).toPlainString());
                }
            } else {
                item[3] = "0";
            }
            item[4] = productDTO.getStatus() != null && productDTO.getStatus() ? "Hoạt động" : "Ngưng hoạt động";
            item[5] = "";
            item[6] = productDTO.getCreatedDate().toString();
            item[7] = productDTO.getImage();
            listValue.add(item);
        }

        String outputPath = OUT_PATH_EXCEL + WebConstants.ROOT_PRODUCT_EXCEL_FILE;
        return ExcelUtils.export(listValue, templatePath, outputPath, fileName);
    }

    private Map<String, Object> buildProperties4AdminSearch(HttpServletRequest request, ProductCommand command) {
        RequestUtils.initSearchBean(request, command);
        Map<String, Object> properties = new HashMap<>();
        ProductDTO dto = command.getPojo();
        if(dto != null) {
            if (!StringUtils.isEmpty(dto.getCode())) {
                properties.put("code", dto.getCode().trim());
            }
            if (!StringUtils.isEmpty(dto.getName())) {
                properties.put("name", dto.getName().trim());
            }
            if (dto.getCatGroup() != null && !StringUtils.isEmpty(dto.getCatGroup().getCode())) {
                properties.put("catGroup.code", dto.getCatGroup().getCode().trim());
            }
        }
        return properties;
    }

    private void referenceData4Admin(ModelAndView mav) {
        List<CatGroupDTO> catGroups = catGroupService.findAllExcludeParent();
        mav.addObject("catGroups", catGroups);
    }

    private List<Long> convertList(String[] stringList) {
        List<Long> result = new ArrayList<>();
        if(stringList != null && stringList.length > 0) {
            for(String id : stringList) {
                result.add(Long.valueOf(id));
            }
        }
        return result;
    }
}
