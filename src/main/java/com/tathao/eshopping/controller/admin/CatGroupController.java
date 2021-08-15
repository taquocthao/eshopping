package com.tathao.eshopping.controller.admin;

import com.tathao.eshopping.model.command.CatGroupCommand;
import com.tathao.eshopping.model.dto.CatGroupDTO;
import com.tathao.eshopping.service.CatGroupService;
import com.tathao.eshopping.ultils.config.Config;
import com.tathao.eshopping.ultils.CoreConstants;
import com.tathao.eshopping.ultils.RequestUtils;
import com.tathao.eshopping.ultils.excel.ExcelUtils;
import com.tathao.eshopping.validator.CatGroupValidator;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class CatGroupController extends ApplicationObjectSupport {

    private static final Logger logger = Logger.getLogger(CatGroupController.class);
    private static final String OUT_PATH_EXCEL = Config.getInstance().getProperty("output.catgroup");

    @Autowired
    private CatGroupService catGroupService;

    @Autowired
    private CatGroupValidator catGroupValidator;


    @RequestMapping(value = "/admin/catgroup/list.html")
    public ModelAndView list(@ModelAttribute(CoreConstants.FORM_MODEL_KEYS) CatGroupCommand command, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/admin/catgroup/list");
        String crudaction = command.getCrudaction();
        try {
            if(!StringUtils.isEmpty(crudaction) && crudaction.equals(CoreConstants.FORM_ACTION_ACTIVE)) {
                catGroupService.updateStatus(this.convertList(command.getCheckList()), true);
                mav.addObject(CoreConstants.ALTER, CoreConstants.TYPE_SUCCESS);
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("message.notify.active.catgroup.success"));
            } else if(!StringUtils.isEmpty(crudaction) && crudaction.equals(CoreConstants.FORM_ACTION_DEACTIVE)) {
                catGroupService.updateStatus(convertList(command.getCheckList()), false);
                mav.addObject(CoreConstants.ALTER, CoreConstants.TYPE_SUCCESS);
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("message.notify.dactive.catgroup.success"));
            }
            executeSearchListCatGroup(command, request);
        } catch (Exception e){
            if(!StringUtils.isEmpty(crudaction) && crudaction.equals(CoreConstants.FORM_ACTION_ACTIVE)) {
                mav.addObject(CoreConstants.ALTER, CoreConstants.TYPE_DANGER);
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("message.notify.active.catgroup.failure"));
            } else if(!StringUtils.isEmpty(crudaction) && crudaction.equals(CoreConstants.FORM_ACTION_DEACTIVE)) {
                mav.addObject(CoreConstants.ALTER, CoreConstants.TYPE_DANGER);
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("message.notify.dactive.catgroup.failure"));
            }
            logger.error(e.getMessage());
        }
        return mav;
    }

    @RequestMapping(value = "/admin/catgroup/edit.html")
    public ModelAndView edit(@ModelAttribute(CoreConstants.FORM_MODEL_KEY) CatGroupCommand command,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/admin/catgroup/edit");
        CatGroupDTO pojo = command.getPojo();
        String crudaction = command.getCrudaction();
        try {
            if(!StringUtils.isEmpty(crudaction)) {
                catGroupValidator.validate(command, bindingResult);
                if(!bindingResult.hasErrors()) {
                    catGroupService.saveOrUpdate(pojo);
                    if (pojo.getCatGroupId() != null) { // update
                        redirectAttributes.addFlashAttribute(CoreConstants.MESSAGE_RESPONSE,
                                this.getMessageSourceAccessor().getMessage("label.catgroup.edit.successful"));
                    } else { // insert
                        redirectAttributes.addFlashAttribute(CoreConstants.MESSAGE_RESPONSE,
                                this.getMessageSourceAccessor().getMessage("label.catgroup.add.successful"));
                    }
                    redirectAttributes.addFlashAttribute(CoreConstants.ALTER, CoreConstants.TYPE_SUCCESS);
                    return new ModelAndView("redirect:/admin/catgroup/list.html");
                }
            }
            if(pojo != null && !StringUtils.isEmpty(pojo.getCode())) {
                pojo = catGroupService.findByCode(pojo.getCode());
                command.setPojo(pojo);
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            mav.addObject(CoreConstants.ALTER, CoreConstants.TYPE_DANGER);
            if (pojo != null && pojo.getCatGroupId() != null) {
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("label.catgroup.update.failure"));
            } else {
                mav.addObject(CoreConstants.MESSAGE_RESPONSE, this.getMessageSourceAccessor().getMessage("label.catgroup.add.failure"));
            }
        }
        referenceData4AdminEdit(mav);
        return mav;
    }

    @RequestMapping(value = "/ajax/admin/catgroup/export.html")
    @ResponseBody
    public String exportExcelCatGroup(CatGroupCommand command, HttpServletRequest request, HttpServletResponse response) {
        String url = "";
        try {
            executeSearchListCatGroup(command, request);
            url = exportExcel(command, request, response);
        } catch (Exception e){
            logger.error(e.getCause());
        }
        return url;
    }

    private Map<String, Object> buildProperties4AdminSearch(HttpServletRequest request, CatGroupCommand command) {
        RequestUtils.initSearchBean(request, command);
        Map<String, Object> properties = new HashMap<>();
        CatGroupDTO dto = command.getPojo();
        if(dto != null) {
            if (!StringUtils.isEmpty(dto.getCode())) {
                properties.put("code", dto.getCode().trim());
            }
            if (!StringUtils.isEmpty(dto.getName())) {
                properties.put("name", dto.getName().trim());
            }
        }
        return properties;
    }

    private void referenceData4AdminEdit(ModelAndView mav) {
        List<CatGroupDTO> catGroupParents = catGroupService.findParentCatGroups();
        mav.addObject("catGroupParents", catGroupParents);
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

    private void executeSearchListCatGroup(CatGroupCommand command, HttpServletRequest request) {
        Map<String, Object> properties = buildProperties4AdminSearch(request, command);
        StringBuilder whereClause = new StringBuilder("1 = 1");
        if(command.getPojo() != null && command.getPojo().getStatus() != null) {
            whereClause.append(" AND A.status = ").append(command.getPojo().getStatus());
        }
        Object[] result = catGroupService.findByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems(), whereClause.toString());
        command.setListResult((List<CatGroupDTO>) result[1]);
        command.setTotalItems(Integer.parseInt(result[0].toString()));
    }

    private String exportExcel(CatGroupCommand command, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//        String fileName = "/file/catgroup" + cal.get(Calendar.DAY_OF_YEAR) + "_" + System.currentTimeMillis() + ".xlsx";
//        String pathExport = request.getSession().getServletContext().getRealPath(fileName);
//        String templatePath = request.getSession().getServletContext().getRealPath("/file/template-export/catgroup.xlsx");
//        FileInputStream fileInputStream = new FileInputStream(new File(templatePath));

//        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        XSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setWrapText(false);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//
//        int rowIndex = 3;
//        for(CatGroupDTO dto : command.getListResult()) {
//            XSSFRow row = sheet.createRow(rowIndex);
//
//            XSSFCell cell = row.createCell(0);
//            cell.setCellValue(rowIndex - 2);
//            cell.setCellStyle(cellStyle);
//
//            int positionNextCell = 0;
//            addCellValue(row, positionNextCell + 1, dto.getCode(), cellStyle);
//            addCellValue(row, positionNextCell + 2, dto.getName(), cellStyle);
//            addCellValue(row, positionNextCell + 3, dto.getParent() != null ? dto.getParent().getName() : "", cellStyle);
//            String status = "Hoạt động";
//            if(!dto.getStatus()) {
//                status = "Ngưng hoạt động";
//            }
//            addCellValue(row, positionNextCell + 4, status, cellStyle);
//            addCellValue(row, positionNextCell + 5, sdf.format(new Date(dto.getCreatedDate().getTime())), cellStyle);
//            rowIndex++;
//        }
//
//        FileOutputStream fileOutputStream = new FileOutputStream(new File(pathExport));
//        workbook.write(fileOutputStream);
//        fileOutputStream.close();

//        response.setContentType("application/octet-stream");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=catgroup_" + cal.get(Calendar.DAY_OF_YEAR) + "_" + System.currentTimeMillis()  + ".xlsx";
//        response.setHeader(headerKey, headerValue);
//        ServletOutputStream outputStream = response.getOutputStream();

//        workbook.write(outputStream);
//        workbook.close();
//        outputStream.flush();

//        return request.getSession().getServletContext().getContextPath() + fileName;

        String templatePath = request.getSession().getServletContext().getRealPath("/file/template-export/catgroup.xlsx");
        String fileName = "catgroup_";
        List<Object[]> listValue = new ArrayList<>();
        for(CatGroupDTO catGroupDTO : command.getListResult()) {
            Object[] item = new Object[5];
            item[0] = catGroupDTO.getCode();
            item[1] = catGroupDTO.getName();
            item[2] = catGroupDTO.getParent() != null ? catGroupDTO.getParent().getName() : "";
            item[3] = catGroupDTO.getStatus() != null && catGroupDTO.getStatus() ? "Hoạt động" : "Ngưng hoạt động";
            item[4] = catGroupDTO.getCreatedDate().toString();
            listValue.add(item);
        }

        return ExcelUtils.export(listValue, templatePath, OUT_PATH_EXCEL, fileName);
    }

    private void addCellValue(XSSFRow row, Integer position, String value, XSSFCellStyle cellStyle) {
        XSSFCell cell = row.createCell(position);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

}
