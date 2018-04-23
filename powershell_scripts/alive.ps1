Try 
{
	$jAlive = Invoke-RestMethod -Uri "http://localhost:4400/rest/2.0/application/info"
	1
} 
Catch
{
	0
}


