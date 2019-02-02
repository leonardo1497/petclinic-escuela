/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.product;

import java.io.IOException;
import org.springframework.samples.petclinic.medicament.*;
import java.util.Collection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Daniel
 */
@Controller
class ProductController {

    private static final String VIEWS_PRODUCT_CREATE_OR_UPDATE_FORM = "products/createOrUpdateProductForm";
    private final FileStorageService fileStorageService;
    private final ProductRepository products;

    @Autowired
    public ProductController(FileStorageService fileStorageService, ProductRepository clinicService) {
        this.fileStorageService = fileStorageService;
        this.products = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/product/new")
    public String initCreationForm(Map<String, Object> model) {
        Product product = new Product();
        model.put("product", product);
        return VIEWS_PRODUCT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/product/new")
    public String processCreationForm(@Valid Product product, BindingResult result, @RequestParam("imagen") MultipartFile imagen) {
        if (result.hasErrors()) {
            return VIEWS_PRODUCT_CREATE_OR_UPDATE_FORM;
        } else {
            product.setFotografia(imagen.getOriginalFilename());
            String fileName = fileStorageService.store(imagen);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();
            System.out.println("URI:"+fileDownloadUri);
            this.products.save(product);
            return "redirect:/product/find";
        }
    }

    @GetMapping("/product/find")
    public String initFindForm(Map<String, Object> model) {
        Product product = new Product();
        model.put("product", product);
        return "products/products";
    }

    @GetMapping("/products")
    public String processFindForm(Product product, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (product.getNombre() == null) {
            product.setNombre(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Product> results = this.products.findByName(product.getNombre());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("nombre", "notFound", "not found");
            return "products/findProducts";
        } else if (results.size() == 1) {
            // 1 owner found
            product = results.iterator().next();
            return "redirect:/product/" + product.getId();
        } else {
            // multiple owners found
            model.put("selections", results);
            return "products/productsList";
        }
    }

    @GetMapping("/products/report")
    public String processFindFormReport(Product product, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (product.getNombre() == null) {
            product.setNombre(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Product> results = this.products.findByName(product.getNombre());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("nombre", "notFound", "not found");
            return "products/productsListNoFunction";
        } else if (results.size() == 1) {
            // 1 owner found
            product = results.iterator().next();
            return "redirect:/product/" + product.getId();
        } else {
            // multiple owners found
            model.put("selections", results);
            return "products/productsListNoFunction";
        }
    }

    @GetMapping("/product/{prodId}")
    public ModelAndView showOwner(@PathVariable("prodId") int prodId) {
        ModelAndView mav = new ModelAndView("products/productDetails");
        mav.addObject(this.products.findById(prodId));
        return mav;
    }

    @GetMapping("/product/delete/{prodId}")
    public String deleteMedicament(Product product, BindingResult result, Map<String, Object> model, @PathVariable("prodId") int prodId) {
        this.products.delete(this.products.findById(prodId));
        Collection<Product> results = this.products.findByName("");
        model.put("selections", results);
        return "redirect:/products";
    }

    @GetMapping("/product/edit/{prodId}")
    public String initUpdateMedicamentForm(@PathVariable("prodId") int prodId, Model model) {
        Product product = this.products.findById(prodId);
        model.addAttribute(product);
        return VIEWS_PRODUCT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/product/edit/{prodId}")
    public String processUpdateOwnerForm(@Valid Product product, BindingResult result, @PathVariable("prodId") int prodId) {
        if (result.hasErrors()) {
            return VIEWS_PRODUCT_CREATE_OR_UPDATE_FORM;
        } else {
            product.setId(prodId);
            this.products.save(product);
            return "redirect:/product/{prodId}";
        }
    }
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
