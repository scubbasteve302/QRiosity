package com.qriosity.mvc.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.qriosity.mvc.model.SharedJsonItem;
import com.qriosity.mvc.model.WedgieResponse;
import com.qriosity.service.ProductDetailService;
import com.qriosity.service.VendorWidgetService;

import com.qriosity.service.WedgiesService;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author yoandy
 * @since 10/5/13
 */
@Controller
@RequestMapping("/widget")
public class WidgetController {
    @Resource
    private VendorWidgetService vendorWidgetService;
    @Resource
    private ProductDetailService productDetailService;
    @Autowired
    private WedgiesService wedgiesService;

    @RequestMapping(value = "/vendor/{vendor}/itemId/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SharedJsonItem> vendorInfo(@PathVariable("vendor") final String vendor, @PathVariable("itemId") final String itemId) {
        return vendorWidgetService.getProductRelatedData(vendor, itemId);
    }

    @RequestMapping(value = "/itemId/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public SharedJsonItem itemInfo(@PathVariable("itemId") final String itemId) {
        return productDetailService.getProductDetail(itemId);
    }

    @RequestMapping(value = "/wedgies/create", method = RequestMethod.GET)
    @ResponseBody
    public WedgieResponse createWedgie(@RequestParam("ids")  final String commaDelimitedIds) {
        final String[] splitIds = commaDelimitedIds.split(",");
        return wedgiesService.createWedgie(Arrays.asList(splitIds));
    }

}
