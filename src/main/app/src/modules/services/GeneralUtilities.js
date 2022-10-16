export const camelCase = (string) => {
  let newString = string[0].toLowerCase() + string.substring(1);
  newString = newString.replaceAll(" ", "");
  return newString;
}
