

function getDivTagContents(str, pos)
	local i = 0
	local divOpen, divOpenEnd, divClose, divCloseEnd = 0, 0, 0, 0
	local contentBegin = string.find(str, ">", pos)
	repeat
		divOpen, divOpenEnd = string.find(str, "<div", pos)
		divClose, divCloseEnd = string.find(str, "</div>", pos)
		
		if divOpen and divClose and divOpen < divClose then
			i = i + 1
			pos = divOpenEnd
		else
			i = i - 1
			pos = divCloseEnd
		end
	until i <= 0
	
	if contentBegin and i == 0 then
		return string.sub(str, contentBegin+1, divClose-1), divCloseEnd+1
	else
		return nil
	end
end

