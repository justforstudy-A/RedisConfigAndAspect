简单整合redis和springboot  注解配置

运用切面设置redis的注解的过期时间
<table>
   <tr>
      <th>星期</th>
      <th>上下午</th>
      <th>课程</th>
      <th>是否必须</th>
   </tr>
   <tr>
      <th>星期一</th>
      <td>上午</td>
      <td>语文</td>
      <td></td>
   </tr>
   <tr>
      <th>星期二</th>
      <td>上午</td>
      <td>数学</td>
      <td></td>
   </tr>
   <tr>
      <th rowspan="2">星期三</th>
      <td rowspan="2">全天</td>
      <td>自然</td>
      <td>是</td>
   </tr>
   <tr>
      <td>美术</td>
      <td>否</td>
   </tr>
   <tr>
    <th rowspan="2">星期四</th>
      <td>上午</td>
      <td>计算机</td>
      <td>是</td>
   </tr>
   <tr>
      <td>上午</td>
      <td>体育</td>
      <td>是</td>
   </tr>
</table>
