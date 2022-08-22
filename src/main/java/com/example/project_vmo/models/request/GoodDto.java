package com.example.project_vmo.models.request;

import java.util.List;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodDto {

  @Id
  private int goodsId;
  private String goodsName;
  private int quantity;
//  private List<ImageDto> images;
//  private Date create_at;
//  private Date updated_at;
//  private Boolean is_deleted = Boolean.FALSE;
//  private MultipartFile[] images;


}