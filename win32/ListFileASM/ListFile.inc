; List File Dialog (c) 2013 khoahv@hotmail.com

; Resource
IDD_DLG_FL        = 101
;ID_BTN_SS         = 1001
IDC_BTN_SS        = 1001
;IDC_EDIT1         = 1002
IDC_EDIT_PATH     = 1002
ID_TXT_PATH       = 1003
;IDC_LIST1         = 1005
IDC_LIST_PATH     = 1005
IDC_CHCK_HIDDEN   = 1006
IDC_CHCK_SYSTEM   = 1007
IDC_CHCK_NORMAL   = 1008
IDC_BUTTON1       = 1009
IDC_BTN_CLOSE     = 1009
IDC_CHCK_ALL      = 1010
; End Resource
 
; ConstDcl
MAX_PATH		=	260
WM_INITDIALOG	=	110H
WM_SETFOCUS		=	7H
WM_COMMAND		=	111H
WM_DESTROY		=	2H
BM_GETCHECK		=	0F0H
BM_SETCHECK		=	0F1H
MB_ICONINFORMATION	=	40H
LB_RESETCONTENT	=	184H
LB_DIR			=	18dH
LBS_SORT		=	2H
DDL_READWRITE	=	0000H; -> Normal Files
DDL_READONLY	=	0001H; -> Read-only Files
DDL_HIDDEN		=	0002H; -> Hidden Files
DDL_SYSTEM		=	0004H; -> System Files
DDL_DIRECTORY	=	0010H; -> Directory
DDL_ARCHIVE		=	0020H; -> Archive Files
; End ConstDcl