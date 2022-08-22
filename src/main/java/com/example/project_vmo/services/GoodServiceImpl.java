package com.example.project_vmo.services;
import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.models.entities.Good;
import com.example.project_vmo.models.entities.Image;
import com.example.project_vmo.models.request.GoodDto;
import com.example.project_vmo.models.response.GoodResponse;
import com.example.project_vmo.repositories.GoodRepo;
import com.example.project_vmo.repositories.ImageRepo;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class GoodServiceImpl implements GoodService {

  @Autowired
  private GoodRepo goodRepo;
  @Autowired
  private ImageRepo imageRepo;

  public static String uploadDir =
      System.getProperty("user.dir") + "/src/main/resources/static/images";


  @Override
  @Transactional
  public GoodResponse getAllGoods(int pageNo,int pageSize) {
    Page<Good> goods = goodRepo.findAll(PageRequest.of(pageNo,pageSize));
    List<GoodDto> content = goods.getContent().stream().map(good -> MapperUtil.map(good,GoodDto.class)).collect(Collectors.toList());
    GoodResponse response = new GoodResponse();
    response.setContent(content);
    response.setPageNo(pageNo);
    response.setPageSize(pageSize);
    response.setTotalElements(goods.getTotalElements());
    response.setTotalPages(goods.getTotalPages());
    response.setLast(goods.isLast());
    return response;
  }

  @Override
  @Transactional
  public GoodDto findByGoodName(String name) {
    Good good = goodRepo.findByGoodsName(name);
    return MapperUtil.map(good,GoodDto.class);
  }

  @Override
  @Transactional
  public GoodDto createGood(GoodDto goodDto,MultipartFile[] files) throws IOException {
    Good good = MapperUtil.map(goodDto, Good.class);
    List<Image> image = new ArrayList<>();
    for (MultipartFile item : files) {
      {
        Image itemImage = new Image();
        String imageUUID = item.getOriginalFilename();
        Path filenamePath = Paths.get(uploadDir, imageUUID);
        Files.write(filenamePath, item.getBytes());
        itemImage.setGoods(good);
        itemImage.setName(imageUUID);
        image.add(itemImage);
      }
    }
    imageRepo.saveAll(image);
    return MapperUtil.map(goodRepo.save(good), GoodDto.class);
  }

  @Override
  @Transactional
  public GoodDto updateGood(GoodDto goodDto, int id) {
    Good good = MapperUtil.map(goodDto,Good.class);
    good.setGoodsId(id);
    return MapperUtil.map(goodRepo.save(good), GoodDto.class);
  }

  @Override
  @Transactional
  public void deleteGood(int id) {
    Good good = goodRepo.findByGoodsId(id);
    good.setIs_deleted(true);
    goodRepo.save(good);
  }
}
