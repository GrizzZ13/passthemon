//该函数用于限制Input只能输入数字和小数点，并且只能保留小数点后两位
export function CheckPrice(obj) {
  let result = obj;
  result = result.replace(/[^\d.]/g, ''); //清除"数字"和"."以外的字符
  result = result.replace(/^\./g, ''); //验证第一个字符是数字而不是
  result = result.replace(/\.{2,}/g, '.'); //只保留第一个. 清除多余的
  result = result.replace('.', '$#$').replace(/\./g, '').replace('$#$', '.');
  result = result.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
  return result;
}
//该函数用于限制input只能输入正整数
export function CheckPositive(obj) {
  return obj.replace(/^(0+)|[^\d]+/g, '');
}
