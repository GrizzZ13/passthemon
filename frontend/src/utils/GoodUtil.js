import * as GoodsConstant from "../config/GoodConstant";

export function getDatetime() {
  let date = new Date();
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  let Hours = date.getHours();
  let Minutes = date.getMinutes();
  let Seconds = date.getSeconds();
  if (month < 10) {
    month = '0' + month;
  }
  if (day < 10) {
    day = '0' + day;
  }
  let datetime =
    year +
    '-' +
    month +
    '-' +
    day +
    ' ' +
    Hours +
    ':' +
    Minutes +
    ':' +
    Seconds;
  return datetime;
}
export function getTypeConstant(string){
    if(string==="服饰装扮")
      return GoodsConstant.CLOTHES;
    if(string==="酒水零食")
      return  GoodsConstant.DRINKS_AND_SNACKS;
    if (string=="电子产品")
      return GoodsConstant.ELECTRONIC_PRODUCTS;
    if(string=="游戏道具")
      return GoodsConstant.GAME_PROPS;
    if(string=="纸质资料")
      return GoodsConstant.PAPER;
    if(string=="生活用品")
      return GoodsConstant.DAILY_USE;
    if(string=="其他")
      return  GoodsConstant.OTHER_KINDS;
}
export function getWearConstant(string){
  if(string=="全新未用")
    return  GoodsConstant.BRAND_NEW;
  if(string=="几乎全新")
    return  GoodsConstant.ALMOST_BRAND_NEW;
  if(string=="轻微磨损")
    return  GoodsConstant.MILD_WEAR;
  if(string=="中度磨损")
    return  GoodsConstant.MODERATE_WEAR;
  if(string=="其他")
    return  GoodsConstant.OTHER_WEAR;
}
export function getTag(num){
    switch (num) {
      case GoodsConstant.CLOTHES:
        return "服饰装扮";
      case GoodsConstant.DRINKS_AND_SNACKS:
        return "酒水零食";
      case GoodsConstant.ELECTRONIC_PRODUCTS:
        return "电子产品";
      case GoodsConstant.GAME_PROPS:
        return "游戏道具";
      case GoodsConstant.PAPER:
        return "纸质资料";
      case GoodsConstant.DAILY_USE:
        return "生活用品";
      case GoodsConstant.OTHER_KINDS:
        return "其他";
      case GoodsConstant.BRAND_NEW:
        return "全新未用";
      case GoodsConstant.ALMOST_BRAND_NEW:
        return "几乎全新";
      case GoodsConstant.MILD_WEAR:
        return "轻微磨损";
      case GoodsConstant.MODERATE_WEAR:
        return "中度磨损";
      case GoodsConstant.OTHER_WEAR:
        return "其他";
    }
}
