

```python
import urllib
from urllib.request import urlopen
from bs4 import BeautifulSoup
import zipfile, io
import pandas as pd
from time import strptime
from datetime import datetime
from sklearn import linear_model
from sklearn.linear_model import LinearRegression,Ridge,Lasso, RandomizedLasso
import requests
from requests import session
import numpy as np
import math
from sklearn.feature_selection import RFE
from sklearn import preprocessing
from sklearn.metrics import mean_squared_error, mean_absolute_error
from sklearn.preprocessing import StandardScaler
```


```python
from sklearn import *
from sklearn.cross_validation import train_test_split
from sklearn.metrics import *
from IPython.display import HTML, display
import statsmodels.api as sm
from statsmodels.formula.api import ols
from sklearn.preprocessing import LabelEncoder
from sklearn.linear_model import LinearRegression
from sklearn.ensemble import RandomForestRegressor
from sklearn.neighbors import KNeighborsRegressor
from sklearn import datasets, linear_model
from sklearn.neural_network import MLPRegressor
from sknn.mlp import Regressor, Layer
```

    /gpfs/fs01/user/se3f-812b629ef574a0-aa895337ea30/.local/lib/python3.5/site-packages/sklearn/cross_validation.py:44: DeprecationWarning: This module was deprecated in version 0.18 in favor of the model_selection module into which all the refactored classes and functions are moved. Also note that the interface of the new CV iterators are different from that of this module. This module will be removed in 0.20.
      "This module will be removed in 0.20.", DeprecationWarning)
    /gpfs/fs01/user/se3f-812b629ef574a0-aa895337ea30/.local/lib/python3.5/site-packages/sklearn/grid_search.py:43: DeprecationWarning: This module was deprecated in version 0.18 in favor of the model_selection module into which all the refactored classes and functions are moved. This module will be removed in 0.20.
      DeprecationWarning)
    /gpfs/fs01/user/se3f-812b629ef574a0-aa895337ea30/.local/lib/python3.5/site-packages/sklearn/lda.py:6: DeprecationWarning: lda.LDA has been moved to discriminant_analysis.LinearDiscriminantAnalysis in 0.17 and will be removed in 0.19
      "in 0.17 and will be removed in 0.19", DeprecationWarning)
    /gpfs/fs01/user/se3f-812b629ef574a0-aa895337ea30/.local/lib/python3.5/site-packages/sklearn/learning_curve.py:23: DeprecationWarning: This module was deprecated in version 0.18 in favor of the model_selection module into which all the functions are moved. This module will be removed in 0.20
      DeprecationWarning)
    /gpfs/fs01/user/se3f-812b629ef574a0-aa895337ea30/.local/lib/python3.5/site-packages/sklearn/qda.py:6: DeprecationWarning: qda.QDA has been moved to discriminant_analysis.QuadraticDiscriminantAnalysis in 0.17 and will be removed in 0.19.
      "in 0.17 and will be removed in 0.19.", DeprecationWarning)



```python
from io import StringIO
import requests
import json
import pandas as pd

# @hidden_cell
# This function accesses a file in your Object Storage. The definition contains your credentials.
# You might want to remove those credentials before you share your notebook.
def get_object_storage_file_with_credentials_37f4b508255f4f0187ff7d11d2ac7164(container, filename):
    """This functions returns a StringIO object containing
    the file content from Bluemix Object Storage."""

    url1 = ''.join(['https://identity.open.softlayer.com', '/v3/auth/tokens'])
    data = {'auth': {'identity': {'methods': ['password'],
            'password': {'user': {'name': 'member_9f8ae4124ed5935b494820bc4caad226a8faaec9','domain': {'id': '3a7926077f894d6098a1599233ed725d'},
            'password': 'b5P&=(MSdQ?_ryx4'}}}}}
    headers1 = {'Content-Type': 'application/json'}
    resp1 = requests.post(url=url1, data=json.dumps(data), headers=headers1)
    resp1_body = resp1.json()
    for e1 in resp1_body['token']['catalog']:
        if(e1['type']=='object-store'):
            for e2 in e1['endpoints']:
                        if(e2['interface']=='public'and e2['region']=='dallas'):
                            url2 = ''.join([e2['url'],'/', container, '/', filename])
    s_subject_token = resp1.headers['x-subject-token']
    headers2 = {'X-Auth-Token': s_subject_token, 'accept': 'application/json'}
    resp2 = requests.get(url=url2, headers=headers2)
    return StringIO(resp2.text)

df_data_1 = pd.read_csv(get_object_storage_file_with_credentials_37f4b508255f4f0187ff7d11d2ac7164('Lendingclub', 'Lending_ModelFile.csv'))
df_data_1.head()
```

    /usr/local/src/conda3_runtime/4.1.1/lib/python3.5/site-packages/IPython/core/interactiveshell.py:2723: DtypeWarning: Columns (45) have mixed types. Specify dtype option on import or set low_memory=False.
      interactivity=interactivity, compiler=compiler, result=result)





<div>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Unnamed: 0</th>
      <th>id</th>
      <th>loan_amnt</th>
      <th>funded_amnt</th>
      <th>funded_amnt_inv</th>
      <th>term</th>
      <th>int_rate</th>
      <th>installment</th>
      <th>grade</th>
      <th>emp_title</th>
      <th>...</th>
      <th>purpose_num</th>
      <th>application_type_num</th>
      <th>loan_status_num</th>
      <th>issue_d_year</th>
      <th>issue_d_month</th>
      <th>earliest_cr_line_year</th>
      <th>earliest_cr_line_month</th>
      <th>last_credit_pull_month</th>
      <th>last_credit_pull_year</th>
      <th>last_credit_pull_monthYear</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>1077501</td>
      <td>5000.0</td>
      <td>5000.0</td>
      <td>4975.0</td>
      <td>36.0</td>
      <td>10.65</td>
      <td>162.87</td>
      <td>B</td>
      <td>Job Title not given</td>
      <td>...</td>
      <td>1</td>
      <td>1</td>
      <td>1.0</td>
      <td>2011</td>
      <td>12</td>
      <td>1985</td>
      <td>1</td>
      <td>3</td>
      <td>2017</td>
      <td>3/2017</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>1077430</td>
      <td>2500.0</td>
      <td>2500.0</td>
      <td>2500.0</td>
      <td>60.0</td>
      <td>15.27</td>
      <td>59.83</td>
      <td>C</td>
      <td>Ryder</td>
      <td>...</td>
      <td>2</td>
      <td>1</td>
      <td>2.0</td>
      <td>2011</td>
      <td>12</td>
      <td>1999</td>
      <td>4</td>
      <td>10</td>
      <td>2016</td>
      <td>10/2016</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>1077175</td>
      <td>2400.0</td>
      <td>2400.0</td>
      <td>2400.0</td>
      <td>36.0</td>
      <td>15.96</td>
      <td>84.33</td>
      <td>C</td>
      <td>Job Title not given</td>
      <td>...</td>
      <td>3</td>
      <td>1</td>
      <td>1.0</td>
      <td>2011</td>
      <td>12</td>
      <td>2001</td>
      <td>11</td>
      <td>3</td>
      <td>2017</td>
      <td>3/2017</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>1076863</td>
      <td>10000.0</td>
      <td>10000.0</td>
      <td>10000.0</td>
      <td>36.0</td>
      <td>13.49</td>
      <td>339.31</td>
      <td>C</td>
      <td>AIR RESOURCES BOARD</td>
      <td>...</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>2011</td>
      <td>12</td>
      <td>1996</td>
      <td>2</td>
      <td>4</td>
      <td>2016</td>
      <td>4/2016</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>1075358</td>
      <td>3000.0</td>
      <td>3000.0</td>
      <td>3000.0</td>
      <td>60.0</td>
      <td>12.69</td>
      <td>67.79</td>
      <td>B</td>
      <td>University Medical Group</td>
      <td>...</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>2011</td>
      <td>12</td>
      <td>1996</td>
      <td>1</td>
      <td>1</td>
      <td>2017</td>
      <td>1/2017</td>
    </tr>
  </tbody>
</table>
<p>5 rows × 106 columns</p>
</div>




```python
#Drop extra columns
def dropExtraColumns(df):
    df = df.drop(['earliest_cr_line_month','last_credit_pull_month','mo_sin_rcnt_rev_tl_op','title','id',
                        'mths_since_recent_bc','num_accts_ever_120_pd','num_actv_bc_tl',
                        'num_actv_rev_tl','num_bc_sats','num_bc_tl','num_il_tl','num_op_rev_tl','num_rev_accts',
                        'num_rev_tl_bal_gt_0','num_tl_120dpd_2m','num_tl_30dpd','num_tl_90g_dpd_24m','num_tl_op_past_12m',
                        'pct_tl_nvr_dlq','emp_title','mo_sin_rcnt_tl','id','percent_bc_gt_75',
                        'total_rec_late_fee','zip_code'],axis=1)
    return df
```


```python
#Derive new columns
def deriveColumn(df):
    df['credit_history']= (df['last_credit_pull_year'] - df['earliest_cr_line_year'])
    df['grade_On_Inquiries']=np.where((df['inq_last_6mths']==0),'A', np.where(df['inq_last_6mths'].between(1,2), 'B',
           np.where(df['inq_last_6mths'].between(3,6), 'C',
           np.where(df['inq_last_6mths'].between(7,10), 'D','E'))))
    df = df[df.loan_status_num!= 8 ]
    df['UPB'] = (df['loan_amnt'] - df['total_rec_prncp']+df['total_rec_int'])
    df.loan_status_num.fillna(1,inplace=True)
    return df
    
```


```python
df_data_1 = dropExtraColumns(df_data_1)
```


```python
df_data_1 = deriveColumn(df_data_1)
```


```python
df_data_1['grade_On_Inquiries'] = pd.factorize(df_data_1.grade_On_Inquiries)[0]
```


```python
df_data_1.corr()
```




<div>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Unnamed: 0</th>
      <th>id</th>
      <th>loan_amnt</th>
      <th>funded_amnt</th>
      <th>funded_amnt_inv</th>
      <th>term</th>
      <th>int_rate</th>
      <th>installment</th>
      <th>emp_length</th>
      <th>annual_inc</th>
      <th>...</th>
      <th>application_type_num</th>
      <th>loan_status_num</th>
      <th>issue_d_year</th>
      <th>issue_d_month</th>
      <th>earliest_cr_line_year</th>
      <th>earliest_cr_line_month</th>
      <th>last_credit_pull_month</th>
      <th>last_credit_pull_year</th>
      <th>credit_history</th>
      <th>grade_On_Inquiries</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>Unnamed: 0</th>
      <td>1.000000</td>
      <td>-0.241488</td>
      <td>0.032021</td>
      <td>0.032931</td>
      <td>0.034571</td>
      <td>0.040296</td>
      <td>-0.007727</td>
      <td>0.018651</td>
      <td>0.007579</td>
      <td>-0.007974</td>
      <td>...</td>
      <td>-0.056407</td>
      <td>0.069333</td>
      <td>-0.008537</td>
      <td>-0.529875</td>
      <td>-0.035369</td>
      <td>-0.000419</td>
      <td>0.017682</td>
      <td>0.020538</td>
      <td>0.037333</td>
      <td>-0.000098</td>
    </tr>
    <tr>
      <th>id</th>
      <td>-0.241488</td>
      <td>1.000000</td>
      <td>0.021643</td>
      <td>0.023237</td>
      <td>0.026389</td>
      <td>-0.005537</td>
      <td>-0.074034</td>
      <td>0.016515</td>
      <td>-0.005260</td>
      <td>0.039190</td>
      <td>...</td>
      <td>0.093668</td>
      <td>-0.170017</td>
      <td>0.896724</td>
      <td>0.143871</td>
      <td>0.117473</td>
      <td>-0.002436</td>
      <td>-0.284080</td>
      <td>0.409835</td>
      <td>-0.076670</td>
      <td>0.073299</td>
    </tr>
    <tr>
      <th>loan_amnt</th>
      <td>0.032021</td>
      <td>0.021643</td>
      <td>1.000000</td>
      <td>0.999542</td>
      <td>0.998505</td>
      <td>0.398112</td>
      <td>0.142841</td>
      <td>0.946518</td>
      <td>0.120921</td>
      <td>0.322301</td>
      <td>...</td>
      <td>0.028881</td>
      <td>0.020064</td>
      <td>0.056238</td>
      <td>-0.024312</td>
      <td>-0.168011</td>
      <td>-0.013057</td>
      <td>-0.011425</td>
      <td>0.044712</td>
      <td>0.172107</td>
      <td>0.029519</td>
    </tr>
    <tr>
      <th>funded_amnt</th>
      <td>0.032931</td>
      <td>0.023237</td>
      <td>0.999542</td>
      <td>1.000000</td>
      <td>0.999057</td>
      <td>0.397436</td>
      <td>0.142929</td>
      <td>0.947169</td>
      <td>0.121022</td>
      <td>0.322201</td>
      <td>...</td>
      <td>0.028978</td>
      <td>0.019740</td>
      <td>0.059479</td>
      <td>-0.024695</td>
      <td>-0.167552</td>
      <td>-0.013079</td>
      <td>-0.012260</td>
      <td>0.046904</td>
      <td>0.171866</td>
      <td>0.029689</td>
    </tr>
    <tr>
      <th>funded_amnt_inv</th>
      <td>0.034571</td>
      <td>0.026389</td>
      <td>0.998505</td>
      <td>0.999057</td>
      <td>1.000000</td>
      <td>0.397702</td>
      <td>0.143069</td>
      <td>0.946071</td>
      <td>0.121554</td>
      <td>0.321864</td>
      <td>...</td>
      <td>0.029085</td>
      <td>0.018761</td>
      <td>0.067714</td>
      <td>-0.024274</td>
      <td>-0.166670</td>
      <td>-0.013143</td>
      <td>-0.013625</td>
      <td>0.053833</td>
      <td>0.171671</td>
      <td>0.030079</td>
    </tr>
    <tr>
      <th>term</th>
      <td>0.040296</td>
      <td>-0.005537</td>
      <td>0.398112</td>
      <td>0.397436</td>
      <td>0.397702</td>
      <td>1.000000</td>
      <td>0.411325</td>
      <td>0.137639</td>
      <td>0.076761</td>
      <td>0.056713</td>
      <td>...</td>
      <td>0.015756</td>
      <td>0.071688</td>
      <td>0.015515</td>
      <td>0.000783</td>
      <td>-0.048280</td>
      <td>-0.000768</td>
      <td>-0.010847</td>
      <td>0.044076</td>
      <td>0.052549</td>
      <td>0.000756</td>
    </tr>
    <tr>
      <th>int_rate</th>
      <td>-0.007727</td>
      <td>-0.074034</td>
      <td>0.142841</td>
      <td>0.142929</td>
      <td>0.143069</td>
      <td>0.411325</td>
      <td>1.000000</td>
      <td>0.146934</td>
      <td>-0.002988</td>
      <td>-0.078792</td>
      <td>...</td>
      <td>0.059135</td>
      <td>0.182120</td>
      <td>-0.050487</td>
      <td>0.016985</td>
      <td>0.115196</td>
      <td>0.001498</td>
      <td>0.055450</td>
      <td>-0.009715</td>
      <td>-0.115931</td>
      <td>-0.170485</td>
    </tr>
    <tr>
      <th>installment</th>
      <td>0.018651</td>
      <td>0.016515</td>
      <td>0.946518</td>
      <td>0.947169</td>
      <td>0.946071</td>
      <td>0.137639</td>
      <td>0.146934</td>
      <td>1.000000</td>
      <td>0.103409</td>
      <td>0.311648</td>
      <td>...</td>
      <td>0.033039</td>
      <td>0.023272</td>
      <td>0.049123</td>
      <td>-0.024000</td>
      <td>-0.144585</td>
      <td>-0.013152</td>
      <td>-0.001297</td>
      <td>0.032269</td>
      <td>0.147495</td>
      <td>0.007315</td>
    </tr>
    <tr>
      <th>emp_length</th>
      <td>0.007579</td>
      <td>-0.005260</td>
      <td>0.120921</td>
      <td>0.121022</td>
      <td>0.121554</td>
      <td>0.076761</td>
      <td>-0.002988</td>
      <td>0.103409</td>
      <td>1.000000</td>
      <td>0.090984</td>
      <td>...</td>
      <td>-0.006247</td>
      <td>-0.017831</td>
      <td>0.012882</td>
      <td>-0.003298</td>
      <td>-0.133849</td>
      <td>-0.014206</td>
      <td>-0.014223</td>
      <td>0.029587</td>
      <td>0.136515</td>
      <td>0.002425</td>
    </tr>
    <tr>
      <th>annual_inc</th>
      <td>-0.007974</td>
      <td>0.039190</td>
      <td>0.322301</td>
      <td>0.322201</td>
      <td>0.321864</td>
      <td>0.056713</td>
      <td>-0.078792</td>
      <td>0.311648</td>
      <td>0.090984</td>
      <td>1.000000</td>
      <td>...</td>
      <td>-0.025012</td>
      <td>-0.035746</td>
      <td>0.040852</td>
      <td>0.001731</td>
      <td>-0.136518</td>
      <td>-0.015670</td>
      <td>-0.021274</td>
      <td>0.020688</td>
      <td>0.138298</td>
      <td>-0.013481</td>
    </tr>
    <tr>
      <th>zip_code</th>
      <td>0.007422</td>
      <td>-0.011771</td>
      <td>-0.000883</td>
      <td>-0.000865</td>
      <td>-0.000807</td>
      <td>-0.024845</td>
      <td>-0.002905</td>
      <td>0.007135</td>
      <td>-0.013535</td>
      <td>-0.004237</td>
      <td>...</td>
      <td>0.006641</td>
      <td>-0.002558</td>
      <td>-0.007535</td>
      <td>-0.009134</td>
      <td>0.006780</td>
      <td>-0.007276</td>
      <td>0.023145</td>
      <td>-0.026176</td>
      <td>-0.009358</td>
      <td>-0.001313</td>
    </tr>
    <tr>
      <th>addr_state</th>
      <td>-0.002844</td>
      <td>0.034293</td>
      <td>-0.009915</td>
      <td>-0.009745</td>
      <td>-0.009474</td>
      <td>0.024224</td>
      <td>0.011405</td>
      <td>-0.017070</td>
      <td>0.014663</td>
      <td>-0.035139</td>
      <td>...</td>
      <td>0.014547</td>
      <td>-0.002366</td>
      <td>0.041445</td>
      <td>-0.000835</td>
      <td>-0.004763</td>
      <td>0.003674</td>
      <td>-0.017000</td>
      <td>0.028656</td>
      <td>0.007591</td>
      <td>-0.017595</td>
    </tr>
    <tr>
      <th>dti</th>
      <td>-0.002019</td>
      <td>0.019136</td>
      <td>0.004727</td>
      <td>0.004798</td>
      <td>0.004940</td>
      <td>0.011696</td>
      <td>0.023488</td>
      <td>0.004386</td>
      <td>-0.008013</td>
      <td>-0.028558</td>
      <td>...</td>
      <td>0.076453</td>
      <td>0.004783</td>
      <td>0.021024</td>
      <td>0.000740</td>
      <td>-0.001042</td>
      <td>0.000116</td>
      <td>-0.008146</td>
      <td>0.015685</td>
      <td>0.002593</td>
      <td>-0.000131</td>
    </tr>
    <tr>
      <th>delinq_2yrs</th>
      <td>0.003288</td>
      <td>0.043811</td>
      <td>-0.002211</td>
      <td>-0.001998</td>
      <td>-0.001684</td>
      <td>-0.004943</td>
      <td>0.043840</td>
      <td>0.005698</td>
      <td>0.025220</td>
      <td>0.042946</td>
      <td>...</td>
      <td>0.001907</td>
      <td>0.002941</td>
      <td>0.052707</td>
      <td>0.006794</td>
      <td>-0.076725</td>
      <td>0.002000</td>
      <td>-0.021390</td>
      <td>0.041213</td>
      <td>0.080654</td>
      <td>-0.017670</td>
    </tr>
    <tr>
      <th>fico_range_low</th>
      <td>-0.041274</td>
      <td>-0.027957</td>
      <td>0.111000</td>
      <td>0.110051</td>
      <td>0.108868</td>
      <td>-0.000487</td>
      <td>-0.402465</td>
      <td>0.062336</td>
      <td>0.020459</td>
      <td>0.075297</td>
      <td>...</td>
      <td>-0.013812</td>
      <td>-0.083244</td>
      <td>-0.070924</td>
      <td>0.002398</td>
      <td>-0.114991</td>
      <td>-0.006068</td>
      <td>0.027079</td>
      <td>-0.103880</td>
      <td>0.104481</td>
      <td>0.085714</td>
    </tr>
    <tr>
      <th>fico_range_high</th>
      <td>-0.041273</td>
      <td>-0.027955</td>
      <td>0.110999</td>
      <td>0.110050</td>
      <td>0.108867</td>
      <td>-0.000488</td>
      <td>-0.402462</td>
      <td>0.062336</td>
      <td>0.020459</td>
      <td>0.075298</td>
      <td>...</td>
      <td>-0.013812</td>
      <td>-0.083243</td>
      <td>-0.070922</td>
      <td>0.002397</td>
      <td>-0.114993</td>
      <td>-0.006068</td>
      <td>0.027079</td>
      <td>-0.103879</td>
      <td>0.104484</td>
      <td>0.085714</td>
    </tr>
    <tr>
      <th>inq_last_6mths</th>
      <td>-0.001980</td>
      <td>-0.108491</td>
      <td>-0.024132</td>
      <td>-0.024397</td>
      <td>-0.025276</td>
      <td>0.001928</td>
      <td>0.219174</td>
      <td>0.004450</td>
      <td>-0.001624</td>
      <td>0.031624</td>
      <td>...</td>
      <td>-0.009227</td>
      <td>0.076157</td>
      <td>-0.104869</td>
      <td>-0.009822</td>
      <td>-0.014441</td>
      <td>-0.002016</td>
      <td>0.053521</td>
      <td>-0.063085</td>
      <td>0.008168</td>
      <td>-0.678284</td>
    </tr>
    <tr>
      <th>mths_since_last_delinq</th>
      <td>-0.004903</td>
      <td>-0.067110</td>
      <td>0.008921</td>
      <td>0.008542</td>
      <td>0.008664</td>
      <td>0.006241</td>
      <td>-0.065581</td>
      <td>-0.003919</td>
      <td>-0.048369</td>
      <td>-0.057273</td>
      <td>...</td>
      <td>-0.003890</td>
      <td>0.001148</td>
      <td>-0.077017</td>
      <td>-0.011100</td>
      <td>0.128998</td>
      <td>0.001555</td>
      <td>0.033668</td>
      <td>-0.061186</td>
      <td>-0.134802</td>
      <td>0.030613</td>
    </tr>
    <tr>
      <th>open_acc</th>
      <td>0.016923</td>
      <td>0.061678</td>
      <td>0.193180</td>
      <td>0.193513</td>
      <td>0.193931</td>
      <td>0.080941</td>
      <td>-0.007384</td>
      <td>0.179448</td>
      <td>0.056019</td>
      <td>0.137094</td>
      <td>...</td>
      <td>0.018792</td>
      <td>0.002161</td>
      <td>0.084996</td>
      <td>-0.006810</td>
      <td>-0.129652</td>
      <td>0.004675</td>
      <td>-0.037732</td>
      <td>0.072176</td>
      <td>0.136543</td>
      <td>-0.104082</td>
    </tr>
    <tr>
      <th>pub_rec</th>
      <td>0.003304</td>
      <td>0.074823</td>
      <td>-0.069831</td>
      <td>-0.069589</td>
      <td>-0.069037</td>
      <td>-0.023699</td>
      <td>0.053348</td>
      <td>-0.057026</td>
      <td>0.011318</td>
      <td>-0.003244</td>
      <td>...</td>
      <td>0.003039</td>
      <td>-0.001247</td>
      <td>0.086701</td>
      <td>-0.000800</td>
      <td>-0.053675</td>
      <td>-0.001438</td>
      <td>-0.032926</td>
      <td>0.057257</td>
      <td>0.059237</td>
      <td>-0.046621</td>
    </tr>
    <tr>
      <th>revol_bal</th>
      <td>0.009733</td>
      <td>0.015673</td>
      <td>0.331083</td>
      <td>0.331028</td>
      <td>0.330693</td>
      <td>0.087359</td>
      <td>-0.039422</td>
      <td>0.312233</td>
      <td>0.092133</td>
      <td>0.290396</td>
      <td>...</td>
      <td>0.008530</td>
      <td>-0.020025</td>
      <td>0.026283</td>
      <td>-0.007695</td>
      <td>-0.213162</td>
      <td>-0.015904</td>
      <td>-0.017744</td>
      <td>0.025159</td>
      <td>0.215234</td>
      <td>0.014454</td>
    </tr>
    <tr>
      <th>revol_util</th>
      <td>0.047413</td>
      <td>-0.096401</td>
      <td>0.118387</td>
      <td>0.118646</td>
      <td>0.119023</td>
      <td>0.082955</td>
      <td>0.242022</td>
      <td>0.128820</td>
      <td>0.042867</td>
      <td>0.037781</td>
      <td>...</td>
      <td>0.017551</td>
      <td>0.052772</td>
      <td>-0.065145</td>
      <td>-0.016011</td>
      <td>-0.024353</td>
      <td>0.004771</td>
      <td>-0.000362</td>
      <td>0.034788</td>
      <td>0.027749</td>
      <td>0.065192</td>
    </tr>
    <tr>
      <th>total_acc</th>
      <td>0.032049</td>
      <td>-0.011996</td>
      <td>0.218147</td>
      <td>0.218095</td>
      <td>0.218191</td>
      <td>0.102163</td>
      <td>-0.040831</td>
      <td>0.194222</td>
      <td>0.099018</td>
      <td>0.178229</td>
      <td>...</td>
      <td>0.009966</td>
      <td>0.000586</td>
      <td>0.017412</td>
      <td>-0.015053</td>
      <td>-0.272769</td>
      <td>0.003785</td>
      <td>-0.012076</td>
      <td>0.031275</td>
      <td>0.275329</td>
      <td>-0.111039</td>
    </tr>
    <tr>
      <th>out_prncp</th>
      <td>-0.154401</td>
      <td>0.530963</td>
      <td>0.533644</td>
      <td>0.534663</td>
      <td>0.535871</td>
      <td>0.333662</td>
      <td>0.069764</td>
      <td>0.453374</td>
      <td>0.064271</td>
      <td>0.176407</td>
      <td>...</td>
      <td>0.085279</td>
      <td>-0.215249</td>
      <td>0.472615</td>
      <td>0.074722</td>
      <td>-0.039129</td>
      <td>-0.006632</td>
      <td>-0.276888</td>
      <td>0.312397</td>
      <td>0.069978</td>
      <td>0.067304</td>
    </tr>
    <tr>
      <th>out_prncp_inv</th>
      <td>-0.154352</td>
      <td>0.530933</td>
      <td>0.533660</td>
      <td>0.534680</td>
      <td>0.535894</td>
      <td>0.333722</td>
      <td>0.069709</td>
      <td>0.453356</td>
      <td>0.064296</td>
      <td>0.176429</td>
      <td>...</td>
      <td>0.085238</td>
      <td>-0.215249</td>
      <td>0.472599</td>
      <td>0.074687</td>
      <td>-0.039150</td>
      <td>-0.006635</td>
      <td>-0.276884</td>
      <td>0.312392</td>
      <td>0.069999</td>
      <td>0.067322</td>
    </tr>
    <tr>
      <th>total_pymnt</th>
      <td>0.177347</td>
      <td>-0.481484</td>
      <td>0.631345</td>
      <td>0.630899</td>
      <td>0.629154</td>
      <td>0.177344</td>
      <td>0.118505</td>
      <td>0.633339</td>
      <td>0.086348</td>
      <td>0.198823</td>
      <td>...</td>
      <td>-0.045944</td>
      <td>-0.107507</td>
      <td>-0.384070</td>
      <td>-0.098029</td>
      <td>-0.163477</td>
      <td>-0.008180</td>
      <td>0.150644</td>
      <td>-0.192628</td>
      <td>0.144086</td>
      <td>-0.022107</td>
    </tr>
    <tr>
      <th>total_pymnt_inv</th>
      <td>0.179239</td>
      <td>-0.478524</td>
      <td>0.631581</td>
      <td>0.631234</td>
      <td>0.631296</td>
      <td>0.177955</td>
      <td>0.118892</td>
      <td>0.633536</td>
      <td>0.087066</td>
      <td>0.198848</td>
      <td>...</td>
      <td>-0.045814</td>
      <td>-0.107752</td>
      <td>-0.375687</td>
      <td>-0.097669</td>
      <td>-0.162774</td>
      <td>-0.008259</td>
      <td>0.149331</td>
      <td>-0.184918</td>
      <td>0.144147</td>
      <td>-0.021769</td>
    </tr>
    <tr>
      <th>total_rec_prncp</th>
      <td>0.161797</td>
      <td>-0.450432</td>
      <td>0.537769</td>
      <td>0.537369</td>
      <td>0.535650</td>
      <td>0.044002</td>
      <td>-0.008090</td>
      <td>0.564457</td>
      <td>0.073384</td>
      <td>0.190265</td>
      <td>...</td>
      <td>-0.048476</td>
      <td>-0.171358</td>
      <td>-0.363709</td>
      <td>-0.091051</td>
      <td>-0.153866</td>
      <td>-0.007706</td>
      <td>0.159973</td>
      <td>-0.231965</td>
      <td>0.130600</td>
      <td>-0.010362</td>
    </tr>
    <tr>
      <th>total_rec_int</th>
      <td>0.137207</td>
      <td>-0.336821</td>
      <td>0.617259</td>
      <td>0.616899</td>
      <td>0.615991</td>
      <td>0.492405</td>
      <td>0.430471</td>
      <td>0.542725</td>
      <td>0.086418</td>
      <td>0.134108</td>
      <td>...</td>
      <td>-0.015112</td>
      <td>0.031075</td>
      <td>-0.254971</td>
      <td>-0.072715</td>
      <td>-0.118187</td>
      <td>-0.005918</td>
      <td>-0.006490</td>
      <td>0.038085</td>
      <td>0.121725</td>
      <td>-0.041395</td>
    </tr>
    <tr>
      <th>total_rec_late_fee</th>
      <td>0.012433</td>
      <td>-0.048033</td>
      <td>0.052005</td>
      <td>0.051909</td>
      <td>0.050939</td>
      <td>0.015198</td>
      <td>0.060331</td>
      <td>0.060519</td>
      <td>-0.001467</td>
      <td>0.023445</td>
      <td>...</td>
      <td>-0.002359</td>
      <td>0.127223</td>
      <td>-0.046854</td>
      <td>-0.010130</td>
      <td>-0.002465</td>
      <td>-0.000200</td>
      <td>0.033709</td>
      <td>-0.011983</td>
      <td>0.001274</td>
      <td>-0.015250</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>num_sats</th>
      <td>0.009202</td>
      <td>0.041727</td>
      <td>0.187624</td>
      <td>0.187827</td>
      <td>0.187938</td>
      <td>0.080223</td>
      <td>-0.011591</td>
      <td>0.173368</td>
      <td>0.051044</td>
      <td>0.132906</td>
      <td>...</td>
      <td>0.018363</td>
      <td>0.005137</td>
      <td>0.048443</td>
      <td>-0.008518</td>
      <td>-0.124055</td>
      <td>0.005055</td>
      <td>-0.027135</td>
      <td>0.042162</td>
      <td>0.127985</td>
      <td>-0.102937</td>
    </tr>
    <tr>
      <th>num_tl_120dpd_2m</th>
      <td>0.001329</td>
      <td>0.004957</td>
      <td>-0.000764</td>
      <td>-0.000738</td>
      <td>-0.000699</td>
      <td>-0.001078</td>
      <td>0.007275</td>
      <td>0.000612</td>
      <td>0.002521</td>
      <td>0.005654</td>
      <td>...</td>
      <td>0.000021</td>
      <td>-0.000021</td>
      <td>0.007307</td>
      <td>-0.002973</td>
      <td>-0.007980</td>
      <td>0.000365</td>
      <td>-0.002112</td>
      <td>0.003929</td>
      <td>0.008353</td>
      <td>0.000759</td>
    </tr>
    <tr>
      <th>num_tl_30dpd</th>
      <td>0.000161</td>
      <td>0.013991</td>
      <td>0.004633</td>
      <td>0.004692</td>
      <td>0.004786</td>
      <td>0.001350</td>
      <td>0.016083</td>
      <td>0.006970</td>
      <td>0.008954</td>
      <td>0.013155</td>
      <td>...</td>
      <td>0.002013</td>
      <td>-0.000440</td>
      <td>0.016736</td>
      <td>-0.000775</td>
      <td>-0.022750</td>
      <td>-0.000341</td>
      <td>-0.004596</td>
      <td>0.008866</td>
      <td>0.023583</td>
      <td>0.005694</td>
    </tr>
    <tr>
      <th>num_tl_90g_dpd_24m</th>
      <td>0.006161</td>
      <td>0.027394</td>
      <td>-0.020404</td>
      <td>-0.020240</td>
      <td>-0.019898</td>
      <td>-0.009960</td>
      <td>0.026960</td>
      <td>-0.014939</td>
      <td>0.000774</td>
      <td>0.008725</td>
      <td>...</td>
      <td>-0.000907</td>
      <td>0.000458</td>
      <td>0.036996</td>
      <td>0.001796</td>
      <td>-0.033377</td>
      <td>0.002832</td>
      <td>-0.014481</td>
      <td>0.028340</td>
      <td>0.036117</td>
      <td>-0.019771</td>
    </tr>
    <tr>
      <th>num_tl_op_past_12m</th>
      <td>-0.001606</td>
      <td>0.073505</td>
      <td>-0.027515</td>
      <td>-0.027469</td>
      <td>-0.027358</td>
      <td>0.007095</td>
      <td>0.211324</td>
      <td>-0.000877</td>
      <td>0.023159</td>
      <td>0.050467</td>
      <td>...</td>
      <td>0.001750</td>
      <td>0.056956</td>
      <td>0.066805</td>
      <td>-0.004983</td>
      <td>0.018613</td>
      <td>-0.000030</td>
      <td>-0.000113</td>
      <td>0.016691</td>
      <td>-0.016924</td>
      <td>-0.241540</td>
    </tr>
    <tr>
      <th>pct_tl_nvr_dlq</th>
      <td>-0.004439</td>
      <td>-0.088072</td>
      <td>0.084434</td>
      <td>0.084081</td>
      <td>0.083274</td>
      <td>0.041087</td>
      <td>-0.058907</td>
      <td>0.067762</td>
      <td>-0.025300</td>
      <td>-0.010903</td>
      <td>...</td>
      <td>0.000357</td>
      <td>0.012626</td>
      <td>-0.100764</td>
      <td>-0.014650</td>
      <td>0.089661</td>
      <td>-0.001282</td>
      <td>0.043487</td>
      <td>-0.075092</td>
      <td>-0.096918</td>
      <td>0.022038</td>
    </tr>
    <tr>
      <th>percent_bc_gt_75</th>
      <td>0.033941</td>
      <td>-0.103874</td>
      <td>0.022334</td>
      <td>0.022275</td>
      <td>0.022075</td>
      <td>0.058665</td>
      <td>0.249722</td>
      <td>0.035557</td>
      <td>0.025905</td>
      <td>-0.018418</td>
      <td>...</td>
      <td>0.017062</td>
      <td>0.060900</td>
      <td>-0.089003</td>
      <td>-0.009277</td>
      <td>-0.019037</td>
      <td>0.007498</td>
      <td>0.011927</td>
      <td>-0.005081</td>
      <td>0.018497</td>
      <td>0.048183</td>
    </tr>
    <tr>
      <th>pub_rec_bankruptcies</th>
      <td>-0.010493</td>
      <td>0.017619</td>
      <td>-0.092008</td>
      <td>-0.092049</td>
      <td>-0.103482</td>
      <td>-0.025677</td>
      <td>0.039161</td>
      <td>-0.082273</td>
      <td>-0.005565</td>
      <td>-0.040390</td>
      <td>...</td>
      <td>0.002336</td>
      <td>0.007967</td>
      <td>-0.020403</td>
      <td>-0.007056</td>
      <td>-0.044525</td>
      <td>-0.000594</td>
      <td>-0.003100</td>
      <td>-0.041451</td>
      <td>0.040334</td>
      <td>-0.049966</td>
    </tr>
    <tr>
      <th>tax_liens</th>
      <td>-0.000969</td>
      <td>0.023064</td>
      <td>0.002272</td>
      <td>0.002368</td>
      <td>0.000282</td>
      <td>-0.008355</td>
      <td>0.005638</td>
      <td>0.006777</td>
      <td>0.003143</td>
      <td>0.028833</td>
      <td>...</td>
      <td>-0.000658</td>
      <td>-0.004259</td>
      <td>0.010307</td>
      <td>-0.000536</td>
      <td>-0.021014</td>
      <td>-0.001272</td>
      <td>-0.011659</td>
      <td>-0.015516</td>
      <td>0.019437</td>
      <td>-0.004085</td>
    </tr>
    <tr>
      <th>tot_hi_cred_lim</th>
      <td>-0.005481</td>
      <td>0.051437</td>
      <td>0.341498</td>
      <td>0.341979</td>
      <td>0.342431</td>
      <td>0.104156</td>
      <td>-0.119317</td>
      <td>0.307118</td>
      <td>0.126893</td>
      <td>0.412381</td>
      <td>...</td>
      <td>0.006023</td>
      <td>-0.054054</td>
      <td>0.068369</td>
      <td>0.002287</td>
      <td>-0.196721</td>
      <td>-0.015130</td>
      <td>-0.019060</td>
      <td>0.032422</td>
      <td>0.199544</td>
      <td>-0.011410</td>
    </tr>
    <tr>
      <th>total_bal_ex_mort</th>
      <td>-0.019754</td>
      <td>0.022282</td>
      <td>0.272333</td>
      <td>0.271840</td>
      <td>0.270449</td>
      <td>0.106591</td>
      <td>-0.008374</td>
      <td>0.250707</td>
      <td>0.009571</td>
      <td>0.315590</td>
      <td>...</td>
      <td>0.025521</td>
      <td>-0.010613</td>
      <td>-0.015639</td>
      <td>-0.001620</td>
      <td>-0.114022</td>
      <td>0.002818</td>
      <td>-0.011440</td>
      <td>-0.014742</td>
      <td>0.112338</td>
      <td>-0.022113</td>
    </tr>
    <tr>
      <th>total_bc_limit</th>
      <td>-0.003047</td>
      <td>0.056199</td>
      <td>0.382335</td>
      <td>0.382791</td>
      <td>0.383128</td>
      <td>0.069010</td>
      <td>-0.231884</td>
      <td>0.346805</td>
      <td>0.069109</td>
      <td>0.270319</td>
      <td>...</td>
      <td>-0.001880</td>
      <td>-0.059638</td>
      <td>0.066448</td>
      <td>-0.001873</td>
      <td>-0.236885</td>
      <td>-0.020811</td>
      <td>-0.019872</td>
      <td>0.022480</td>
      <td>0.238645</td>
      <td>0.017338</td>
    </tr>
    <tr>
      <th>total_il_high_credit_limit</th>
      <td>-0.004738</td>
      <td>0.091603</td>
      <td>0.203560</td>
      <td>0.203879</td>
      <td>0.204199</td>
      <td>0.090859</td>
      <td>-0.004352</td>
      <td>0.185011</td>
      <td>-0.000608</td>
      <td>0.264236</td>
      <td>...</td>
      <td>0.035592</td>
      <td>-0.016878</td>
      <td>0.094144</td>
      <td>0.005850</td>
      <td>-0.027353</td>
      <td>0.009692</td>
      <td>-0.038877</td>
      <td>0.059569</td>
      <td>0.033196</td>
      <td>-0.025159</td>
    </tr>
    <tr>
      <th>ficoMean</th>
      <td>-0.041274</td>
      <td>-0.027957</td>
      <td>0.111000</td>
      <td>0.110051</td>
      <td>0.108868</td>
      <td>-0.000487</td>
      <td>-0.402465</td>
      <td>0.062336</td>
      <td>0.020459</td>
      <td>0.075297</td>
      <td>...</td>
      <td>-0.013812</td>
      <td>-0.083244</td>
      <td>-0.070924</td>
      <td>0.002398</td>
      <td>-0.114991</td>
      <td>-0.006068</td>
      <td>0.027079</td>
      <td>-0.103880</td>
      <td>0.104481</td>
      <td>0.085714</td>
    </tr>
    <tr>
      <th>last_ficoMean</th>
      <td>-0.057769</td>
      <td>0.092740</td>
      <td>0.099021</td>
      <td>0.098958</td>
      <td>0.099178</td>
      <td>-0.036395</td>
      <td>-0.312319</td>
      <td>0.076821</td>
      <td>0.025381</td>
      <td>0.060729</td>
      <td>...</td>
      <td>0.007163</td>
      <td>-0.548537</td>
      <td>0.067411</td>
      <td>0.023831</td>
      <td>-0.101455</td>
      <td>-0.000578</td>
      <td>-0.122817</td>
      <td>-0.015504</td>
      <td>0.099721</td>
      <td>0.089774</td>
    </tr>
    <tr>
      <th>grade_num</th>
      <td>-0.026366</td>
      <td>0.023281</td>
      <td>-0.147208</td>
      <td>-0.147085</td>
      <td>-0.146537</td>
      <td>-0.424540</td>
      <td>-0.955217</td>
      <td>-0.140793</td>
      <td>0.004677</td>
      <td>0.072443</td>
      <td>...</td>
      <td>-0.055085</td>
      <td>-0.183019</td>
      <td>0.003188</td>
      <td>-0.004794</td>
      <td>-0.114456</td>
      <td>-0.001828</td>
      <td>-0.037271</td>
      <td>-0.014099</td>
      <td>0.112836</td>
      <td>0.161206</td>
    </tr>
    <tr>
      <th>home_ownership_num</th>
      <td>-0.003099</td>
      <td>-0.001460</td>
      <td>0.189836</td>
      <td>0.189735</td>
      <td>0.189738</td>
      <td>0.106247</td>
      <td>-0.071704</td>
      <td>0.155984</td>
      <td>0.185052</td>
      <td>0.146345</td>
      <td>...</td>
      <td>0.014902</td>
      <td>-0.042525</td>
      <td>0.010656</td>
      <td>0.000219</td>
      <td>-0.196212</td>
      <td>-0.012679</td>
      <td>0.000254</td>
      <td>-0.000087</td>
      <td>0.195818</td>
      <td>-0.019226</td>
    </tr>
    <tr>
      <th>verification_status_num</th>
      <td>-0.020108</td>
      <td>-0.017716</td>
      <td>-0.198062</td>
      <td>-0.197906</td>
      <td>-0.198742</td>
      <td>-0.116791</td>
      <td>-0.225591</td>
      <td>-0.198429</td>
      <td>0.024207</td>
      <td>-0.057632</td>
      <td>...</td>
      <td>-0.026339</td>
      <td>-0.049604</td>
      <td>-0.039084</td>
      <td>-0.016485</td>
      <td>-0.000140</td>
      <td>0.004558</td>
      <td>0.003510</td>
      <td>-0.037707</td>
      <td>-0.003593</td>
      <td>0.030991</td>
    </tr>
    <tr>
      <th>pymnt_plan_num</th>
      <td>-0.001416</td>
      <td>0.001571</td>
      <td>0.000313</td>
      <td>0.000317</td>
      <td>0.000326</td>
      <td>0.001652</td>
      <td>0.001846</td>
      <td>-0.000078</td>
      <td>0.001696</td>
      <td>-0.000671</td>
      <td>...</td>
      <td>-0.000318</td>
      <td>-0.001112</td>
      <td>0.001923</td>
      <td>-0.001791</td>
      <td>-0.000362</td>
      <td>-0.000576</td>
      <td>-0.001237</td>
      <td>0.001387</td>
      <td>0.000498</td>
      <td>-0.000090</td>
    </tr>
    <tr>
      <th>purpose_num</th>
      <td>-0.015196</td>
      <td>0.012234</td>
      <td>-0.033121</td>
      <td>-0.033286</td>
      <td>-0.033495</td>
      <td>0.023087</td>
      <td>0.145280</td>
      <td>-0.024665</td>
      <td>0.019913</td>
      <td>0.004477</td>
      <td>...</td>
      <td>0.002780</td>
      <td>0.027330</td>
      <td>-0.004363</td>
      <td>0.007086</td>
      <td>0.014377</td>
      <td>-0.000414</td>
      <td>0.013954</td>
      <td>-0.019411</td>
      <td>-0.016271</td>
      <td>-0.026193</td>
    </tr>
    <tr>
      <th>application_type_num</th>
      <td>-0.056407</td>
      <td>0.093668</td>
      <td>0.028881</td>
      <td>0.028978</td>
      <td>0.029085</td>
      <td>0.015756</td>
      <td>0.059135</td>
      <td>0.033039</td>
      <td>-0.006247</td>
      <td>-0.025012</td>
      <td>...</td>
      <td>1.000000</td>
      <td>-0.013013</td>
      <td>0.085780</td>
      <td>-0.022509</td>
      <td>0.008637</td>
      <td>-0.001256</td>
      <td>-0.025352</td>
      <td>0.031697</td>
      <td>-0.005482</td>
      <td>0.005053</td>
    </tr>
    <tr>
      <th>loan_status_num</th>
      <td>0.069333</td>
      <td>-0.170017</td>
      <td>0.020064</td>
      <td>0.019740</td>
      <td>0.018761</td>
      <td>0.071688</td>
      <td>0.182120</td>
      <td>0.023272</td>
      <td>-0.017831</td>
      <td>-0.035746</td>
      <td>...</td>
      <td>-0.013013</td>
      <td>1.000000</td>
      <td>-0.142227</td>
      <td>-0.031616</td>
      <td>0.013697</td>
      <td>-0.000974</td>
      <td>0.226201</td>
      <td>-0.062388</td>
      <td>-0.019847</td>
      <td>-0.055451</td>
    </tr>
    <tr>
      <th>issue_d_year</th>
      <td>-0.008537</td>
      <td>0.896724</td>
      <td>0.056238</td>
      <td>0.059479</td>
      <td>0.067714</td>
      <td>0.015515</td>
      <td>-0.050487</td>
      <td>0.049123</td>
      <td>0.012882</td>
      <td>0.040852</td>
      <td>...</td>
      <td>0.085780</td>
      <td>-0.142227</td>
      <td>1.000000</td>
      <td>-0.097178</td>
      <td>0.103185</td>
      <td>-0.004855</td>
      <td>-0.294359</td>
      <td>0.514704</td>
      <td>-0.052028</td>
      <td>0.071149</td>
    </tr>
    <tr>
      <th>issue_d_month</th>
      <td>-0.529875</td>
      <td>0.143871</td>
      <td>-0.024312</td>
      <td>-0.024695</td>
      <td>-0.024274</td>
      <td>0.000783</td>
      <td>0.016985</td>
      <td>-0.024000</td>
      <td>-0.003298</td>
      <td>0.001731</td>
      <td>...</td>
      <td>-0.022509</td>
      <td>-0.031616</td>
      <td>-0.097178</td>
      <td>1.000000</td>
      <td>0.022776</td>
      <td>0.002085</td>
      <td>0.006867</td>
      <td>0.021750</td>
      <td>-0.020578</td>
      <td>0.005307</td>
    </tr>
    <tr>
      <th>earliest_cr_line_year</th>
      <td>-0.035369</td>
      <td>0.117473</td>
      <td>-0.168011</td>
      <td>-0.167552</td>
      <td>-0.166670</td>
      <td>-0.048280</td>
      <td>0.115196</td>
      <td>-0.144585</td>
      <td>-0.133849</td>
      <td>-0.136518</td>
      <td>...</td>
      <td>0.008637</td>
      <td>0.013697</td>
      <td>0.103185</td>
      <td>0.022776</td>
      <td>1.000000</td>
      <td>-0.011760</td>
      <td>-0.008996</td>
      <td>0.029722</td>
      <td>-0.995092</td>
      <td>0.003213</td>
    </tr>
    <tr>
      <th>earliest_cr_line_month</th>
      <td>-0.000419</td>
      <td>-0.002436</td>
      <td>-0.013057</td>
      <td>-0.013079</td>
      <td>-0.013143</td>
      <td>-0.000768</td>
      <td>0.001498</td>
      <td>-0.013152</td>
      <td>-0.014206</td>
      <td>-0.015670</td>
      <td>...</td>
      <td>-0.001256</td>
      <td>-0.000974</td>
      <td>-0.004855</td>
      <td>0.002085</td>
      <td>-0.011760</td>
      <td>1.000000</td>
      <td>-0.000202</td>
      <td>-0.002526</td>
      <td>0.011487</td>
      <td>0.001505</td>
    </tr>
    <tr>
      <th>last_credit_pull_month</th>
      <td>0.017682</td>
      <td>-0.284080</td>
      <td>-0.011425</td>
      <td>-0.012260</td>
      <td>-0.013625</td>
      <td>-0.010847</td>
      <td>0.055450</td>
      <td>-0.001297</td>
      <td>-0.014223</td>
      <td>-0.021274</td>
      <td>...</td>
      <td>-0.025352</td>
      <td>0.226201</td>
      <td>-0.294359</td>
      <td>0.006867</td>
      <td>-0.008996</td>
      <td>-0.000202</td>
      <td>1.000000</td>
      <td>-0.603775</td>
      <td>-0.050794</td>
      <td>-0.038159</td>
    </tr>
    <tr>
      <th>last_credit_pull_year</th>
      <td>0.020538</td>
      <td>0.409835</td>
      <td>0.044712</td>
      <td>0.046904</td>
      <td>0.053833</td>
      <td>0.044076</td>
      <td>-0.009715</td>
      <td>0.032269</td>
      <td>0.029587</td>
      <td>0.020688</td>
      <td>...</td>
      <td>0.031697</td>
      <td>-0.062388</td>
      <td>0.514704</td>
      <td>0.021750</td>
      <td>0.029722</td>
      <td>-0.002526</td>
      <td>-0.603775</td>
      <td>1.000000</td>
      <td>0.069334</td>
      <td>0.043467</td>
    </tr>
    <tr>
      <th>credit_history</th>
      <td>0.037333</td>
      <td>-0.076670</td>
      <td>0.172107</td>
      <td>0.171866</td>
      <td>0.171671</td>
      <td>0.052549</td>
      <td>-0.115931</td>
      <td>0.147495</td>
      <td>0.136515</td>
      <td>0.138298</td>
      <td>...</td>
      <td>-0.005482</td>
      <td>-0.019847</td>
      <td>-0.052028</td>
      <td>-0.020578</td>
      <td>-0.995092</td>
      <td>0.011487</td>
      <td>-0.050794</td>
      <td>0.069334</td>
      <td>1.000000</td>
      <td>0.001097</td>
    </tr>
    <tr>
      <th>grade_On_Inquiries</th>
      <td>-0.000098</td>
      <td>0.073299</td>
      <td>0.029519</td>
      <td>0.029689</td>
      <td>0.030079</td>
      <td>0.000756</td>
      <td>-0.170485</td>
      <td>0.007315</td>
      <td>0.002425</td>
      <td>-0.013481</td>
      <td>...</td>
      <td>0.005053</td>
      <td>-0.055451</td>
      <td>0.071149</td>
      <td>0.005307</td>
      <td>0.003213</td>
      <td>0.001505</td>
      <td>-0.038159</td>
      <td>0.043467</td>
      <td>0.001097</td>
      <td>1.000000</td>
    </tr>
  </tbody>
</table>
<p>92 rows × 92 columns</p>
</div>




```python
df_data_1.head()
```




<div>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Unnamed: 0</th>
      <th>loan_amnt</th>
      <th>funded_amnt</th>
      <th>funded_amnt_inv</th>
      <th>term</th>
      <th>int_rate</th>
      <th>installment</th>
      <th>grade</th>
      <th>emp_length</th>
      <th>home_ownership</th>
      <th>...</th>
      <th>application_type_num</th>
      <th>loan_status_num</th>
      <th>issue_d_year</th>
      <th>issue_d_month</th>
      <th>earliest_cr_line_year</th>
      <th>last_credit_pull_year</th>
      <th>last_credit_pull_monthYear</th>
      <th>credit_history</th>
      <th>grade_On_Inquiries</th>
      <th>UPB</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>5000.0</td>
      <td>5000.0</td>
      <td>4975.0</td>
      <td>36.0</td>
      <td>10.65</td>
      <td>162.87</td>
      <td>B</td>
      <td>10</td>
      <td>RENT</td>
      <td>...</td>
      <td>1</td>
      <td>1.0</td>
      <td>2011</td>
      <td>12</td>
      <td>1985</td>
      <td>2017</td>
      <td>3/2017</td>
      <td>32</td>
      <td>0</td>
      <td>863.16</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>2500.0</td>
      <td>2500.0</td>
      <td>2500.0</td>
      <td>60.0</td>
      <td>15.27</td>
      <td>59.83</td>
      <td>C</td>
      <td>0</td>
      <td>RENT</td>
      <td>...</td>
      <td>1</td>
      <td>2.0</td>
      <td>2011</td>
      <td>12</td>
      <td>1999</td>
      <td>2016</td>
      <td>10/2016</td>
      <td>17</td>
      <td>1</td>
      <td>2478.71</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>2400.0</td>
      <td>2400.0</td>
      <td>2400.0</td>
      <td>36.0</td>
      <td>15.96</td>
      <td>84.33</td>
      <td>C</td>
      <td>10</td>
      <td>RENT</td>
      <td>...</td>
      <td>1</td>
      <td>1.0</td>
      <td>2011</td>
      <td>12</td>
      <td>2001</td>
      <td>2017</td>
      <td>3/2017</td>
      <td>16</td>
      <td>0</td>
      <td>605.67</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>10000.0</td>
      <td>10000.0</td>
      <td>10000.0</td>
      <td>36.0</td>
      <td>13.49</td>
      <td>339.31</td>
      <td>C</td>
      <td>10</td>
      <td>RENT</td>
      <td>...</td>
      <td>1</td>
      <td>1.0</td>
      <td>2011</td>
      <td>12</td>
      <td>1996</td>
      <td>2016</td>
      <td>4/2016</td>
      <td>20</td>
      <td>0</td>
      <td>2214.92</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>3000.0</td>
      <td>3000.0</td>
      <td>3000.0</td>
      <td>60.0</td>
      <td>12.69</td>
      <td>67.79</td>
      <td>B</td>
      <td>1</td>
      <td>RENT</td>
      <td>...</td>
      <td>1</td>
      <td>1.0</td>
      <td>2011</td>
      <td>12</td>
      <td>1996</td>
      <td>2017</td>
      <td>1/2017</td>
      <td>21</td>
      <td>2</td>
      <td>1066.91</td>
    </tr>
  </tbody>
</table>
<p>5 rows × 84 columns</p>
</div>




```python
#Model Metrics dataframe
model_metric = pd.DataFrame({'AcceptedLoan_Model':[],'RMS_train':[],'RMS_test': [],'MAE_train': [],'MAE_test':[],'RMSE_Test':[]})
                             #'MAPE_train':[],'MAPE_test':[]})
RMSE_dict = {}
```


```python
def ModelMetrics(modelname, model, X_train, y_train, X_test, y_test):
    ytrain_pred = model.predict(X_train)
    ytest_pred = model.predict(X_test)
    RMS_train = mean_squared_error(y_train, ytrain_pred)
    RMS_test = mean_squared_error(y_test, ytest_pred)
    MAE_train = mean_absolute_error(y_train, ytrain_pred)
    MAE_test = mean_absolute_error(y_test, ytest_pred)
    RMSE_Test = np.sqrt(mean_squared_error(y_test, ytest_pred))
    #MAPE_train = np.mean(np.abs((y_train - ytrain_pred) / y_train)) * 100
    #MAPE_test = np.mean(np.abs((y_test - ytest_pred) / y_test)) * 100
    RMSE_dict[modelname] = RMS_test
    df_metrics = pd.DataFrame({'AcceptedLoan_Model':[modelname],'RMS_train':[RMS_train],'RMS_test': [RMS_test],'MAE_train': [MAE_train],'MAE_test':[MAE_test],'RMSE_Test':[RMSE_Test]})
                               #'MAPE_train':[MAPE_train],'MAPE_test':[MAPE_test]})   
    global model_metric
    model_metric = pd.concat([model_metric, df_metrics])
    print("Completed",modelname)
    return (model_metric,df_metrics)

```


```python
##df_data_1['loan_status'] = pd.factorize(df_data_1.loan_status)[0]#
df_data_1['home_ownership'] = pd.factorize(df_data_1.home_ownership)[0]
df_data_1['verification_status'] = pd.factorize(df_data_1.verification_status)[0]
df_data_1['purpose'] = pd.factorize(df_data_1.purpose)[0]

```


```python
#collecting columns which are candidates for predicting interest rate
#feature_cols = ['term','inq_last_6mths','application_type_num','delinq_2yrs','home_ownership_num', 'revol_util','ficoMean','loan_amnt',
#                'purpose_num','emp_length','annual_inc','open_acc','credit_history','dti','installment']
#acc_now_delinq+loan_amnt+inq_last_6mths+term+annual_inc+revol_util+ficoMean+installment++loan_status_num+credit_history+open_acc

#'term','ficoMean','loan_amnt','installment','open_acc','credit_history','revol_util','inq_last_6mths','funded_amnt','out_prncp' --RMSE:0.6
#'term','ficoMean','loan_amnt','installment','emp_length','annual_inc','dti','purpose','open_acc','credit_history','funded_amnt','revol_util','inq_last_6mths','out_prncp' -- RMSE: 1.48

feature_cols = ['term',
                'ficoMean',
                'loan_amnt',
                'installment',
                'home_ownership_num',
                'annual_inc',
                'emp_length',
                'purpose_num',
                'open_acc',
                'credit_history',
                'revol_util',
                'inq_last_6mths',
                 'addr_state',
                'loan_status_num']

```


```python
#Split into training and testing datasets
X = df_data_1[feature_cols]
y = df_data_1.int_rate
X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)

```


```python
#Feature Selection: RFE
df_X = df_data_1[feature_cols]
df_y = df_data_1[[5]]

```


```python
lmReg = LinearRegression()
rfe = RFE(estimator=lmReg, n_features_to_select=12, step=1)
```


```python
rfe.fit(df_X,df_y)
```

    /gpfs/fs01/user/se3f-812b629ef574a0-aa895337ea30/.local/lib/python3.5/site-packages/sklearn/utils/validation.py:526: DataConversionWarning: A column-vector y was passed when a 1d array was expected. Please change the shape of y to (n_samples, ), for example using ravel().
      y = column_or_1d(y, warn=True)





    RFE(estimator=LinearRegression(copy_X=True, fit_intercept=True, n_jobs=1, normalize=False),
      n_features_to_select=12, step=1, verbose=0)




```python
ranking = rfe.ranking_
```


```python
print(sorted(zip(map(lambda x: round(x, 2), rfe.ranking_), df_X)))
```

    [(1, 'credit_history'), (1, 'emp_length'), (1, 'ficoMean'), (1, 'home_ownership_num'), (1, 'inq_last_6mths'), (1, 'installment'), (1, 'loan_amnt'), (1, 'loan_status_num'), (1, 'open_acc'), (1, 'purpose_num'), (1, 'revol_util'), (1, 'term'), (2, 'addr_state'), (3, 'annual_inc')]



```python
#Linear Regression
def linearRegression(X_train,y_train,X_test,y_test):
    print("Linear Regression model computation starts")
    lm_model = LinearRegression()
    lm_model.fit(X_train, y_train)
    print("Coefficient is:",lm_model.coef_)
    print("Intercept is:",lm_model.intercept_)
    train_pred = lm_model.predict(X_train)
    print("R-Square Training",r2_score(y_train,train_pred))      
    test_pred = lm_model.predict(X_test)
    print("R-Square Testing",r2_score(y_test, test_pred)) 
    ModelMetrics('Regression',lm_model, X_train, y_train, X_test, y_test)
    print(model_metric)
    return('Linear Regression',lm_model)
```


```python
algo_name,model = linearRegression(X_train,y_train,X_test,y_test)
```

    Linear Regression model computation starts
    Coefficient is: [  4.62297842e-01  -3.16846012e-02  -1.36464427e-03   4.47008055e-02
      -2.09305350e-01  -3.79871032e-06  -1.09434443e-02   1.89022022e-01
      -1.94375285e-02  -3.18253477e-02   1.16685630e-02   6.31845010e-01
       2.28157726e-04   9.39023705e-01]
    Intercept is: 14.5688203328
    R-Square Training 0.614847567625
    R-Square Testing 0.615626946082
    Completed Regression
      AcceptedLoan_Model  MAE_test  MAE_train  RMSE_Test  RMS_test  RMS_train
    0         Regression   2.20135   2.204143   2.834695  8.035495   8.057798



```python
#Ordinary Least Square Assumption
ols_model = ols("int_rate ~df_data_1[feature_cols]", data=df_data_1).fit()

```


```python
ols_model_summary = ols_model.summary()

```


```python
HTML(
ols_model_summary\
.as_html()\
.replace(' Adj. R-squared: ', ' Adj. R-squared: ')\
.replace('coef', 'coef')\
.replace('std err', 'std err')\
.replace('P>|t|', 'P>|t|')\
.replace('[95.0% Conf. Int.]', '[95.0% Conf. Int.]')
)
```




<table class="simpletable">
<caption>OLS Regression Results</caption>
<tr>
  <th>Dep. Variable:</th>        <td>int_rate</td>     <th>  R-squared:         </th>  <td>   0.615</td>  
</tr>
<tr>
  <th>Model:</th>                   <td>OLS</td>       <th>  Adj. R-squared:    </th>  <td>   0.615</td>  
</tr>
<tr>
  <th>Method:</th>             <td>Least Squares</td>  <th>  F-statistic:       </th>  <td>1.505e+05</td> 
</tr>
<tr>
  <th>Date:</th>             <td>Fri, 14 Apr 2017</td> <th>  Prob (F-statistic):</th>   <td>  0.00</td>   
</tr>
<tr>
  <th>Time:</th>                 <td>15:00:41</td>     <th>  Log-Likelihood:    </th> <td>-3.2475e+06</td>
</tr>
<tr>
  <th>No. Observations:</th>      <td>1319098</td>     <th>  AIC:               </th>  <td>6.495e+06</td> 
</tr>
<tr>
  <th>Df Residuals:</th>          <td>1319083</td>     <th>  BIC:               </th>  <td>6.495e+06</td> 
</tr>
<tr>
  <th>Df Model:</th>              <td>    14</td>      <th>                     </th>      <td> </td>     
</tr>
<tr>
  <th>Covariance Type:</th>      <td>nonrobust</td>    <th>                     </th>      <td> </td>     
</tr>
</table>
<table class="simpletable">
<tr>
               <td></td>                  <th>coef</th>     <th>std err</th>      <th>t</th>      <th>P>|t|</th> <th>[95.0% Conf. Int.]</th> 
</tr>
<tr>
  <th>Intercept</th>                   <td>   14.5528</td> <td>    0.076</td> <td>  192.694</td> <td> 0.000</td> <td>   14.405    14.701</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][0]</th>  <td>    0.4632</td> <td>    0.000</td> <td> 1028.306</td> <td> 0.000</td> <td>    0.462     0.464</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][1]</th>  <td>   -0.0317</td> <td> 9.57e-05</td> <td> -331.106</td> <td> 0.000</td> <td>   -0.032    -0.032</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][2]</th>  <td>   -0.0014</td> <td> 1.78e-06</td> <td> -770.431</td> <td> 0.000</td> <td>   -0.001    -0.001</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][3]</th>  <td>    0.0448</td> <td> 5.52e-05</td> <td>  811.730</td> <td> 0.000</td> <td>    0.045     0.045</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][4]</th>  <td>   -0.2090</td> <td>    0.003</td> <td>  -74.808</td> <td> 0.000</td> <td>   -0.214    -0.203</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][5]</th>  <td>-3.534e-06</td> <td> 3.85e-08</td> <td>  -91.836</td> <td> 0.000</td> <td>-3.61e-06 -3.46e-06</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][6]</th>  <td>   -0.0112</td> <td>    0.001</td> <td>  -16.852</td> <td> 0.000</td> <td>   -0.012    -0.010</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][7]</th>  <td>    0.1884</td> <td>    0.001</td> <td>  178.856</td> <td> 0.000</td> <td>    0.186     0.191</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][8]</th>  <td>   -0.0199</td> <td>    0.000</td> <td>  -41.508</td> <td> 0.000</td> <td>   -0.021    -0.019</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][9]</th>  <td>   -0.0318</td> <td>    0.000</td> <td>  -93.128</td> <td> 0.000</td> <td>   -0.033    -0.031</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][10]</th> <td>    0.0116</td> <td>    0.000</td> <td>   95.689</td> <td> 0.000</td> <td>    0.011     0.012</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][11]</th> <td>    0.6323</td> <td>    0.003</td> <td>  230.729</td> <td> 0.000</td> <td>    0.627     0.638</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][12]</th> <td>    0.0001</td> <td>    0.000</td> <td>    0.675</td> <td> 0.500</td> <td>   -0.000     0.001</td>
</tr>
<tr>
  <th>df_data_1[feature_cols][13]</th> <td>    0.9322</td> <td>    0.009</td> <td>  107.785</td> <td> 0.000</td> <td>    0.915     0.949</td>
</tr>
</table>
<table class="simpletable">
<tr>
  <th>Omnibus:</th>       <td>139986.233</td> <th>  Durbin-Watson:     </th>  <td>   1.906</td> 
</tr>
<tr>
  <th>Prob(Omnibus):</th>   <td> 0.000</td>   <th>  Jarque-Bera (JB):  </th> <td>250689.823</td>
</tr>
<tr>
  <th>Skew:</th>            <td> 0.728</td>   <th>  Prob(JB):          </th>  <td>    0.00</td> 
</tr>
<tr>
  <th>Kurtosis:</th>        <td> 4.563</td>   <th>  Cond. No.          </th>  <td>3.18e+06</td> 
</tr>
</table>




```python
##Random Forest Algorithm
def randomForest(X_train,y_train,X_test,y_test):
    print("Random Forest computation starts")
    rForest = RandomForestRegressor(n_estimators=20)
    rForest.fit(X_train, y_train)
    train_pred = rForest.predict(X_train)
    print("R-Square Training",r2_score(y_train,train_pred))      
    test_pred = rForest.predict(X_test)
    print("R-Square Testing",r2_score(y_test, test_pred)) 
    ModelMetrics('Random Forest',rForest, X_train, y_train, X_test, y_test)
    print(model_metric)
    return('Random Forest',rForest)
```


```python
algo_name,model =  randomForest(X_train,y_train,X_test,y_test)
```

    Random Forest computation starts
    R-Square Training 0.988811861218
    R-Square Testing 0.933244807966
    Completed Random Forest
      AcceptedLoan_Model  MAE_test  MAE_train  RMSE_Test  RMS_test  RMS_train
    0         Regression  2.201350   2.204143   2.834695  8.035495   8.057798
    0      Random Forest  0.552383   0.222355   1.181333  1.395548   0.234068



```python
print(pd.DataFrame({'feature':feature_cols, 'importance':model.feature_importances_}).sort('importance'))
```

    /usr/local/src/conda3_runtime/4.1.1/lib/python3.5/site-packages/ipykernel/__main__.py:1: FutureWarning: sort(columns=....) is deprecated, use sort_values(by=.....)
      if __name__ == '__main__':


                   feature  importance
    4   home_ownership_num    0.002330
    13     loan_status_num    0.002354
    6           emp_length    0.005210
    12          addr_state    0.008528
    8             open_acc    0.009193
    9       credit_history    0.011899
    10          revol_util    0.020101
    11      inq_last_6mths    0.024274
    7          purpose_num    0.028694
    5           annual_inc    0.037087
    1             ficoMean    0.154869
    0                 term    0.169313
    2            loan_amnt    0.222890
    3          installment    0.303258



```python
##KNN Algorithm
def knnRegression(X_train, y_train, X_test, y_test):
    print("Knn Regression computation starts")
    knn = KNeighborsRegressor(n_neighbors=3)
    knn.fit(X_train,y_train)     
    test_pred = knn.predict(X_test)
    print("R-Square Testing",r2_score(y_test, test_pred)) 
    ModelMetrics('KNN',knn, X_train, y_train, X_test, y_test)
    print(model_metric)
    return('KNN',knn)
```


```python
algo_name, model = knnRegression(X_train, y_train, X_test, y_test)
```

    Knn Regression computation starts
    R-Square Testing 0.624362133807
    Completed KNN
      AcceptedLoan_Model  MAE_test  MAE_train  RMSE_Test  RMS_test  RMS_train
    0         Regression  2.201350   2.204143   2.834695  8.035495   8.057798
    0      Random Forest  0.552383   0.222355   1.181333  1.395548   0.234068
    0                KNN  1.672386   1.137260   2.802299  7.852882   3.667681



```python
##Neural Network Algorithm
```


```python
def NeuralNetwork(X_train, y_train,X_test,y_test):
    print("Neural Network computation starts")
    scaler = StandardScaler()
    # Fit training data
    scaler.fit(X_train)
    X_train = scaler.transform(X_train)
    X_test = scaler.transform(X_test)
    nn = MLPRegressor()
    nn.fit(X_train,y_train)     
    test_pred = nn.predict(X_test)
    print("R-Square Testing",r2_score(y_test, test_pred)) 
    ModelMetrics('Neural Network',nn, X_train, y_train, X_test, y_test)
    print(model_metric)
    return('Neural Network',nn)    
```


```python
algo_name, model = NeuralNetwork(X_train, y_train,X_test,y_test)
```

    Neural Network computation starts
    R-Square Testing 0.994069601992
    Completed Neural Network
      AcceptedLoan_Model  MAE_test  MAE_train  RMSE_Test  RMS_test  RMS_train
    0         Regression  2.201350   2.204143   2.834695  8.035495   8.057798
    0      Random Forest  0.552383   0.222355   1.181333  1.395548   0.234068
    0                KNN  1.672386   1.137260   2.802299  7.852882   3.667681
    0     Neural Network  0.160248   0.159514   0.352105  0.123978   0.122409



```python
model_metric
```




<div>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>AcceptedLoan_Model</th>
      <th>MAE_test</th>
      <th>MAE_train</th>
      <th>RMSE_Test</th>
      <th>RMS_test</th>
      <th>RMS_train</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>Regression</td>
      <td>2.201350</td>
      <td>2.204143</td>
      <td>2.834695</td>
      <td>8.035495</td>
      <td>8.057798</td>
    </tr>
    <tr>
      <th>0</th>
      <td>Random Forest</td>
      <td>0.552383</td>
      <td>0.222355</td>
      <td>1.181333</td>
      <td>1.395548</td>
      <td>0.234068</td>
    </tr>
    <tr>
      <th>0</th>
      <td>KNN</td>
      <td>1.672386</td>
      <td>1.137260</td>
      <td>2.802299</td>
      <td>7.852882</td>
      <td>3.667681</td>
    </tr>
    <tr>
      <th>0</th>
      <td>Neural Network</td>
      <td>0.160248</td>
      <td>0.159514</td>
      <td>0.352105</td>
      <td>0.123978</td>
      <td>0.122409</td>
    </tr>
  </tbody>
</table>
</div>




```python
#Find Best model
def BestModel(df):
    df['Model_Rank'] = df['RMS_test'].rank(ascending=1)
    print("Model with Model_Rank as 1 is Best Model")
    return df

```


```python
BestModel(model_metric)
```

    Model with Model_Rank as 1 is Best Model





<div>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>AcceptedLoan_Model</th>
      <th>MAE_test</th>
      <th>MAE_train</th>
      <th>RMSE_Test</th>
      <th>RMS_test</th>
      <th>RMS_train</th>
      <th>Model_Rank</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>Regression</td>
      <td>2.201350</td>
      <td>2.204143</td>
      <td>2.834695</td>
      <td>8.035495</td>
      <td>8.057798</td>
      <td>4.0</td>
    </tr>
    <tr>
      <th>0</th>
      <td>Random Forest</td>
      <td>0.552383</td>
      <td>0.222355</td>
      <td>1.181333</td>
      <td>1.395548</td>
      <td>0.234068</td>
      <td>2.0</td>
    </tr>
    <tr>
      <th>0</th>
      <td>KNN</td>
      <td>1.672386</td>
      <td>1.137260</td>
      <td>2.802299</td>
      <td>7.852882</td>
      <td>3.667681</td>
      <td>3.0</td>
    </tr>
    <tr>
      <th>0</th>
      <td>Neural Network</td>
      <td>0.160248</td>
      <td>0.159514</td>
      <td>0.352105</td>
      <td>0.123978</td>
      <td>0.122409</td>
      <td>1.0</td>
    </tr>
  </tbody>
</table>
</div>




```python
#Create clusters using term and home_ownership
df_data_1.home_ownership.replace('NONE','OTHER', inplace=True)
df_data_1.home_ownership.replace('ANY','OTHER', inplace=True)
df_test = {}
df_test2= {}

g1 = df_data_1.groupby('term')
list_keys = [ k for k in g1.groups.keys() ]

i=0
count = 0
while i<len(list_keys):
    df_test[i]=g1.get_group(list_keys[i]).reset_index()  
    g2 = df_test[i].groupby('home_ownership')
    list_keys2 = [ k for k in g2.groups.keys() ]
    j=0
    while j<len(list_keys2):
        df_test2[j]=g2.get_group(list_keys2[j]).reset_index()
        fileName = 'bin' + str(count) + '.csv'
        print(fileName)
        df_test2[j].to_csv(fileName,index=False)
        j=j+1
        count=count+1
    i=i+1
```

    bin0.csv
    bin1.csv
    bin2.csv
    bin3.csv
    bin4.csv
    bin5.csv
    bin6.csv
    bin7.csv



```python
df_bin.home_ownership.replace('NONE','OTHER', inplace=True)
df_bin.home_ownership.replace('ANY','OTHER', inplace=True)
df_bintest = {}
df_bintest2= {}

g1 = df_bin.groupby('term')
list_keys = [ k for k in g1.groups.keys() ]

i=0
count = 0
while i<len(list_keys):
    df_bintest[i]=g1.get_group(list_keys[i]).reset_index()  
    g2 = df_bintest[i].groupby('home_ownership')
    list_keys2 = [ k for k in g2.groups.keys() ]
    j=0
    while j<len(list_keys2):
        df_bintest2[j]=g2.get_group(list_keys2[j]).reset_index()
        fileName = 'newbin' + str(count) + '.csv'
        print(fileName)
        df_bintest2[j].to_csv(fileName,index=False)
        j=j+1
        count=count+1
    i=i+1
```

    newbin0.csv
    newbin1.csv
    newbin2.csv
    newbin3.csv
    newbin4.csv
    newbin5.csv
    newbin6.csv
    newbin7.csv



```python
# @hidden_cell
credentials_38 = {
  'auth_url':'https://identity.open.softlayer.com',
  'project':'object_storage_37f4b508_255f_4f01_87ff_7d11d2ac7164',
  'project_id':'5f7d1f48e2d94194b0cddd68902dd993',
  'region':'dallas',
  'user_id':'b0aee163cb904664afb3a41ec599075c',
  'domain_id':'3a7926077f894d6098a1599233ed725d',
  'domain_name':'1256591',
  'username':'member_9f8ae4124ed5935b494820bc4caad226a8faaec9',
  'password':"""b5P&=(MSdQ?_ryx4""",
  'container':'Lendingclub',
  'tenantId':'undefined',
  'filename':'Model_Term60.csv'
}
```


```python
#For saving CSV Files
from io import BytesIO  
import requests  
import json  
import pandas as pd

def put_file(credentials, local_file_name):  
    """This functions returns a StringIO object containing
    the file content from Bluemix Object Storage V3."""
    f = open(local_file_name,'r')
    my_data = f.read()
    url1 = ''.join(['https://identity.open.softlayer.com', '/v3/auth/tokens'])
    data = {'auth': {'identity': {'methods': ['password'],
            'password': {'user': {'name': credentials['username'],'domain': {'id': credentials['domain_id']},
            'password': credentials['password']}}}}}
    headers1 = {'Content-Type': 'application/json'}
    resp1 = requests.post(url=url1, data=json.dumps(data), headers=headers1)
    resp1_body = resp1.json()
    for e1 in resp1_body['token']['catalog']:
        if(e1['type']=='object-store'):
            for e2 in e1['endpoints']:
                        if(e2['interface']=='public'and e2['region']=='dallas'):
                            url2 = ''.join([e2['url'],'/', credentials['container'], '/', local_file_name])
    s_subject_token = resp1.headers['x-subject-token']
    headers2 = {'X-Auth-Token': s_subject_token, 'accept': 'application/json'}
    resp2 = requests.put(url=url2, headers=headers2, data = my_data )
    print(resp2)
```


```python
put_file(credentials_38, 'newbin7.csv')
```

    <Response [201]>



```python
#drop extra columns
df_data_1 = df_data_1.drop(['funded_amnt_inv','last_credit_pull_month','mo_sin_rcnt_rev_tl_op','title','id',
                        'mths_since_recent_bc','num_accts_ever_120_pd','num_actv_bc_tl',
                        'num_actv_rev_tl','num_bc_sats','num_bc_tl','num_il_tl','num_op_rev_tl','num_rev_accts',
                        'num_rev_tl_bal_gt_0','num_tl_120dpd_2m','num_tl_30dpd','num_tl_90g_dpd_24m','num_tl_op_past_12m',
                        'pct_tl_nvr_dlq','emp_title','mo_sin_rcnt_tl','id','percent_bc_gt_75',
                        'total_rec_late_fee','zip_code'],axis=1)
```


```python
df_bin = pd.DataFrame(df_data_1)
```


```python
df_bin = df_bin.drop(['total_rec_prncp',
 'total_rec_int','total_rev_hi_lim',
 'tot_hi_cred_lim','UPB'],axis=1)
```


```python
list(df_bin)
```




    ['loan_amnt',
     'funded_amnt',
     'term',
     'int_rate',
     'installment',
     'grade',
     'emp_length',
     'home_ownership',
     'annual_inc',
     'issue_d',
     'loan_status',
     'purpose',
     'addr_state',
     'dti',
     'earliest_cr_line',
     'fico_range_low',
     'fico_range_high',
     'inq_last_6mths',
     'open_acc',
     'revol_util',
     'out_prncp',
     'last_pymnt_d',
     'last_credit_pull_d',
     'last_fico_range_high',
     'last_fico_range_low',
     'policy_code',
     'application_type',
     'acc_now_delinq',
     'ficoMean',
     'last_ficoMean',
     'grade_num',
     'home_ownership_num',
     'purpose_num',
     'loan_status_num',
     'issue_d_year',
     'earliest_cr_line_year',
     'last_credit_pull_year',
     'last_credit_pull_monthYear',
     'credit_history',
     'grade_On_Inquiries']




```python
df_bin.shape
```




    (1319098, 40)




```python

```
