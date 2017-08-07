<html>
<body >
	<h2>
		<b><u>URL SHORTENER</u></b>
	</h2>
	<input type="button" value="Create Short URL" id="createShortURL">
	<br>
	<input type="button" value="Get Long URL" id="getLongURL">
	<br>
	<input type="button" value="BlackList" id="blackListShortURL">
	<br>

	<div class="shortURL">
		<h3>Create Short URL</h3>
		<form action="/GenerateURL" method="POST" id="postForm">
			Enter Long URL : <br>
			<textarea width="100" height="500" id="longURLPost"></textarea><br><br> 
			Short URL : <br> <input type="text" size="50" id="shortURLPost"><br> <br>
		</form>
		<input type="button" id="postbutton" value="Submit">
	</div>


	<div class="longURL">
		<h3>Get Long URL</h3>
		<form action="/GenerateURL" id="getForm">
			Enter Short URL : <br> <input type="text" id="shortURLGET" size="50"><br> <br> Long URL : <br>
			<textarea width="100" height="500" id="longURLGET"></textarea><br> <br>
		</form>
		<input type="button" id="getbutton" value="Submit">
	</div>


	<div class="blackList">
		<h3>BlackList Short URL</h3>
		<form action="/GenerateURL" method="PUT" id="putForm">
			Enter Short URL : <br> 
			<input type="text"  size="50" id="shortURLPUT"><br> <br> 
		</form>
		<input type="button" id="putbutton" value="Submit">
	</div>

	</form>
</body>

<head>
<title>URL Shortener</title>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script>
	$(document).ready(function() {
		$(".shortURL").hide();
		$(".longURL").hide();
		$(".blackList").hide();
	});
	$("#createShortURL").click(function() {
		$(".shortURL").show();
		$(".longURL").hide();
		$(".blackList").hide();
	});
	$("#getLongURL").click(function() {
		$(".shortURL").hide();
		$(".longURL").show();
		$(".blackList").hide();
	});
	$("#blackListShortURL").click(function() {
		$(".shortURL").hide();
		$(".longURL").hide();
		$(".blackList").show();
	});

	$("#postbutton").click(function() {
						var url = "http://localhost:8080/MiniURL/GenerateURL"; // the script where you handle the form input.
						$.ajax({
									type : "POST",
									url : url + "?longUrl="+ $("#longURLPost").val(),
									success : function(data) {
										$("#shortURLPost").val("http://www.urlshortener.com/"+ data.shortUrl);
									},
									error : function(data, err) {
										if (data.status == 409) {
											$("#shortURLPost").val("http://www.urlshortener.com/"+ data.responseJSON.shortUrl);
										} else if (data.status == 400) {
											alert("Invalid Long URL!");
											$("#shortURLPost").val("");
										} else if (data.status == 403) {
											alert("BlackListed URL!");
											$("#shortURLPost").val("");
										} else {
											alert("Maintainence Issue! Please try again later");
											$("#shortURLPost").val("");
										}
									}
							});
					});

	$("#getbutton").click(function() {
		var url = "http://localhost:8080/MiniURL/GenerateURL"; // the script where you handle the form input.
		$.ajax({
					type : "GET",
					url : url + "?shortUrl="+ $("#shortURLGET").val(),
					success : function(data) {
						$("#longURLGET").val(data.longUrl);
					},
					error : function(data, err) {
						if (data.status == 400) {
							alert("Invalid Short URL!");
						} else if (data.status == 403) {
							alert("BlackListed URL!");
						}else if (data.status == 404) {
							alert("Short URL Not Found!");
						} else {
							alert("Maintainence Issue! Please try again later");
						}
						$("#longURLGET").val("");
					}
				});
	});

	$("#putbutton").click(function() {
		var url = "http://localhost:8080/MiniURL/GenerateURL"; // the script where you handle the form input.
		$
				.ajax({
					type : "PUT",
					url : url + "?shortUrl="
							+ $("#shortURLPUT").val(),
					success : function(data) {
						alert("URL Blacklisted Successfully!")
					},
					error : function(data, err) {
						if (data.status == 400) {
							alert("Invalid Short URL!");
						} else if (data.status == 404) {
							alert("Short URL Not Found!");
						} else {
							alert("Maintainence Issue! Please try again later");
						}
					}
				});
	});
</script>
</head>


</html>