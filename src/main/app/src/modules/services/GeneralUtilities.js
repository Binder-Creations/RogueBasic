class GeneralUtilities {

  static camelCase(string){
    let newString = string[0].toLowerCase() + string.substring(1);
    newString = newString.replaceAll(" ", "");
    return newString;
  }

}

export default GeneralUtilities;
export const camelCase = GeneralUtilities.camelCase;
