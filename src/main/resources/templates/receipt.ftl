${"item"?right_pad(20)}${"price"?right_pad(20)}${"qty"?right_pad(20)}
<#list receipt.items() as item>
${item.name()?right_pad(20)}$${item.price()?string["0.00"]?right_pad(20)}${item.quantity()?string?right_pad(20)}
</#list>
${"subtotal:"?right_pad(40)}$${receipt.subtotal()?string["0.00"]?right_pad(20)}
${"tax:"?right_pad(40)}$${receipt.tax()?string["0.00"]?right_pad(20)}
${"total:"?right_pad(40)}$${receipt.total()?string["0.00"]?right_pad(20)}