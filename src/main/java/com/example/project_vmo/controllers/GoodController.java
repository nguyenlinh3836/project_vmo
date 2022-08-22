package com.example.project_vmo.controllers;

import com.example.project_vmo.models.request.GoodDto;
import com.example.project_vmo.models.response.GoodResponse;
import com.example.project_vmo.services.GoodService;
import com.example.project_vmo.services.ImageService;
import java.io.IOException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/goods")
public class GoodController {

  @Autowired
  private GoodService goodService;
  @Autowired
  private ImageService imageService;
  @Autowired
  private ModelMapper mapper;

  @GetMapping
  public GoodResponse listGoods(
      @RequestParam(value = "pageNo",defaultValue = "0") int pageNo,
      @RequestParam(value = "pageSize",defaultValue = "5") int pageSize
  ) {
    return goodService.getAllGoods(pageNo, pageSize);
  }

  @PostMapping()
  public ResponseEntity<?> createGood(@RequestPart(value = "good") GoodDto goodDto,
      @RequestPart(value = "files", required = false) MultipartFile[] files) throws IOException {
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(goodService.createGood(goodDto, files));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateGood(@RequestParam GoodDto goodDto, @PathVariable int id) {
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(goodService.updateGood(goodDto, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteGood(@PathVariable int id) {
    goodService.deleteGood(id);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Good has been deleted");
  }


}
