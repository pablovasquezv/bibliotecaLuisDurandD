package com.complejo.educacional.luis.durand.durand.controllers;

import com.complejo.educacional.luis.durand.durand.implementsServices.ICategoriaImplements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping(value = "/biblioteca/v1/")
public class CategoriaRestController {
    @Autowired
    private ICategoriaImplements iCategoriaImplements;

}
