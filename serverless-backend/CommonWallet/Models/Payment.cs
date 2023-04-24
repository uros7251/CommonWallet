using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CommonWallet.Models
{
	public class Payment
	{
        [JsonProperty("payment_id")]
        public int PaymentId { get; set; }

        [JsonProperty("payer_name")]
        public string PayerName { get; set; }

        [JsonProperty("description")]
        public string Description { get; set; }

        [JsonProperty("amount")]
        public double Amount { get; set; }

        [JsonProperty("payment_time")]
        public DateTime PaymentTime { get; set; }
    }
}
