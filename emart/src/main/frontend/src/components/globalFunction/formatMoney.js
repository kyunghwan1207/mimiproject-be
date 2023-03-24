export function formatMoney(inputVal) {
    const outputVal = inputVal.toString()
    .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
  return outputVal+'원';
}