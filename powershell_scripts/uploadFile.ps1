[xml]$config = Get-Content -Path $args[0]

$pair = "$($config.collibra.username):$($config.collibra.password)"
$encoded = [System.Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes($pair))

$basic = "Basic $encoded"
$Headers = @{
	Authorization = $basic
} 


$fullURL = $config.collibra.url + "/rest/2.0/someURIProcess"

$output = Invoke-RestMethod -Method 'Post' -Headers $Headers -ContentType "application/json" -Uri $fullURL -Body $bodyVar