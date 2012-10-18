--LIBERRIES!
--io = require("io")
http = require("socket.http")
ltn12 = require("ltn12")
string = require("string")

-- Constants
RAW_CARD_INFO_FILE = "raw_card_info.txt"
CARD_INFO_FILE = "card_info.txt"

-- Set output file for raw output
local file_out = io.open(RAW_CARD_INFO_FILE, "w")

-- Pull the html list of names
local body = http.request{
	url = "http://magiccards.info/rtr/en.html",
	sink = ltn12.sink.file(file_out)}

-- Read in raw html list and swap output to new file
--io.close(file_out)
local file_in = io.open(RAW_CARD_INFO_FILE, "r")
local rawCardInfo = file_in:read("*a")
file_out = io.open(CARD_INFO_FILE, "w")

-- Gather card names into an array
local cardInfo = {}
local currCardNum = 1
string.gsub(rawCardInfo, '<td><a href="/rtr/en/%d*.html">(.-)</a></td>', function(capture)
	cardInfo[currCardNum] = capture
	currCardNum = currCardNum + 1
	return ""
end)

-- Print cardnames to output file
for i=1, table.getn(cardInfo) do
	file_out:write(cardInfo[i].."\n")
end


