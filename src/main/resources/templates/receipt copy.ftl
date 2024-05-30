<#assign space=" ">
${"item"?right_pad(15)}${space?right_pad(5)}${"price"?right_pad(10)}${"qty"?right_pad(5)}
<#list receipt.items() as item>
${item.name()?right_pad(15)}${space?right_pad(5)}${item.price()?string?right_pad(10)}${space?right_pad(5)}${item.quantity()?string?right_pad(5)}
</#list>
${"subtotal:"?right_pad(25)}${receipt.subtotal()?string?right_pad(10)}
${"tax:"?right_pad(25)}${receipt.tax()?string?right_pad(10)}
${"total:"?right_pad(25)}${receipt.total()?string?right_pad(10)}

<html>
    <table>
    <tr>
        <th>item</th>
        <th>price</th>
        <th>qty</th>
    </tr>
    <#list receipt.items() as item>
    <tr>
        <td>${item.name()}</td>
        <td>${item.price()?string}</td>
        <td>${item.quantity()?string}</td>
    </tr>
    </#list>
    <tr>
        <td>subtotal:</td>
        <td colspan="2">${receipt.subtotal()?string}</td>
    </tr>
    <tr>
        <td>tax:</td>
        <td colspan="2">${receipt.tax()?string}</td>
    </tr>
    <tr>
        <td>total:</td>
        <td colspan="2">${receipt.total()?string}</td>
    </tr>
    </table>
</html>